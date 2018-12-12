
package analysis;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import graph.model.GMethodNode;
import graph.model.GNodeType;
import util.UtilPlatform;
import util.UtilMsg;

public class DeleteAnalyzer {
   private static final String JAVANATURE = "org.eclipse.jdt.core.javanature";
   protected String prjName, pkgName;

   private GMethodNode methodToBeDeleted;
   private IMethod methodElemToBeDeleted;
   private ICompilationUnit iCUnitToBeDeleted;

   public DeleteAnalyzer() {
   }

   public void analyze() {
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
         if (iPackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
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
      for (ICompilationUnit iCUnit : iCompilationUnits) {
         CompilationUnit compUnit = parse(iCUnit);

         // TODO: Term Project - Complete
         
         ASTVisitor astVisitor = new ASTVisitor() {

            public boolean visit(MethodDeclaration methodDecl) {
               if (eqSrc(methodDecl)) {
                  methodElemToBeDeleted = (IMethod) methodDecl.resolveBinding().getJavaElement();
                  iCUnitToBeDeleted = iCUnit;
               }
               return true;
            }

            private boolean eqSrc(MethodDeclaration methodDecl) {
               ITypeBinding curClass = methodDecl.resolveBinding().getDeclaringClass();
               String cur = curClass.getPackage().getName() + "." + curClass.getName() + "." + methodDecl.getName();
               String src = methodToBeDeleted.getPkgName() + "." + methodToBeDeleted.getClassName() + "." + methodToBeDeleted.getName();
               return cur.equals(src);
            }
         };
         compUnit.accept(astVisitor);;
      }
   }

   private static CompilationUnit parse(ICompilationUnit unit) {
      ASTParser parser = ASTParser.newParser(AST.JLS10);
      parser.setKind(ASTParser.K_COMPILATION_UNIT);
      parser.setSource(unit);
      parser.setResolveBindings(true);
      return (CompilationUnit) parser.createAST(null); // parse
   }

   public void setMethodToBeDeleted(GMethodNode mNode) {
      this.methodToBeDeleted = mNode;
   }

   public void deleteMethod() {
      // TODO: Term Project - Complete
      if (this.methodToBeDeleted.getNodeType().equals(GNodeType.UserSelection)){
         System.out.println("DELETE -> " + this.methodElemToBeDeleted.getPath() + "." + this.methodElemToBeDeleted.getElementName());
         try {
         
            //See https://help.eclipse.org/oxygen/index.jsp?topic=%2Forg.eclipse.jdt.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fjdt%2Fcore%2FIMethod.html
            methodElemToBeDeleted.delete(true, null);
            
            UtilPlatform.indentAndSave(iCUnitToBeDeleted);
         } catch (JavaModelException e) {
            e.printStackTrace();
         }
      } else {
         System.out.println("[DBG] Please select method node to delete.");
      }
      UtilMsg.openWarning("Method succesfully deleted.");
   }
}