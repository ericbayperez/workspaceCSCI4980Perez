/*
 * @(#) ASTAnalyzer.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package analysis;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import graph.model.GClassNode;
import graph.model.GMethodNode;
import graph.provider.GModelProvider;
import visitor.DeclarationVisitor;
import visitor.ExpandDeclarationVisitor;

public class MethodAnalyzer {
   private static final String JAVANATURE = "org.eclipse.jdt.core.javanature";
   protected String prjName, pkgName;
   GMethodNode mNode = null;

   public void analyze(GMethodNode mNode) {
      GModelProvider.instance().reset();
      this.mNode = mNode;

      // =============================================================
      // 1st step: Project
      // =============================================================
      try {
         IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
         for (IProject project : projects) {
            if (!project.isOpen() || !project.isNatureEnabled(JAVANATURE)) { // Check if we have a Java project.
               continue;
            }
            prjName = project.getName();
            analyzePackages(JavaCore.create(project).getPackageFragments());
         }
      } catch (JavaModelException e) {
         e.printStackTrace();
      } catch (CoreException e) {
         e.printStackTrace();
      }
   }

   protected void analyzePackages(IPackageFragment[] packages) throws CoreException, JavaModelException {
      // =============================================================
      // 2nd step: Packages
      // =============================================================
      for (IPackageFragment iPackage : packages) {
         if (iPackage.getKind() == IPackageFragmentRoot.K_SOURCE && mNode.getPkgName().equals(iPackage.getElementName())) {
            if (iPackage.getCompilationUnits().length < 1) {
               continue;
            }
            pkgName = iPackage.getElementName();
            analyzeCompilationUnit(iPackage.getCompilationUnits());
         }
      }
   }

   private void analyzeCompilationUnit(ICompilationUnit[] iCompilationUnits) throws JavaModelException {
      // =============================================================
      // 3rd step: ICompilationUnits
      // =============================================================
      for (ICompilationUnit iUnit : iCompilationUnits) {
    	  if(iUnit.getElementName().equals(mNode.getClassName() + ".java")) {
	         CompilationUnit compilationUnit = parse(iUnit);
	         ExpandDeclarationVisitor declVisitor = new ExpandDeclarationVisitor();
	         declVisitor.acceptSelectedMethodNode(mNode);
	         compilationUnit.accept(declVisitor);
    	  }
      }
   }

   private static CompilationUnit parse(ICompilationUnit unit) {
      ASTParser parser = ASTParser.newParser(AST.JLS10);
      parser.setKind(ASTParser.K_COMPILATION_UNIT);
      parser.setSource(unit);
      parser.setResolveBindings(true);
      return (CompilationUnit) parser.createAST(null); // parse
   }
}