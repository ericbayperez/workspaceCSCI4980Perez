package visitor;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import graph.model.GClassNode;
import graph.model.GConnection;
import graph.model.GMethodNode;
import graph.model.GNode;
import graph.model.GPackageNode;
import graph.provider.GModelProvider;

public class ExpandDeclarationVisitor extends ASTVisitor {

	GMethodNode mNode = null;
	
	public void acceptSelectedMethodNode(GMethodNode mNode) {
		this.mNode = mNode;
	}
	
	public boolean visit(PackageDeclaration pkgDecl) {
		insertPackageNode(pkgDecl);
		return super.visit(pkgDecl);
	}
	
	private void insertPackageNode(PackageDeclaration pkgDecl) {
		IPackageBinding rBinding = pkgDecl.resolveBinding();
		String prjName = rBinding.getJavaElement().getJavaProject().getElementName();
		String nodeName = pkgDecl.getName().getFullyQualifiedName();
		String id = prjName + "." + nodeName;
		if (GModelProvider.instance().getNodeMap().containsKey(id) == false) {
			addNode(new GPackageNode(id, nodeName, prjName));
		}
	}

	/**
	 * A type declaration is the union of a class declaration
	 * and an interface declaration.
	 */
	@Override
	public boolean visit(TypeDeclaration typeDecl) {
		GNode typeNode = insertTypeNode(typeDecl);
		GNode pkgGNode = GModelProvider.instance().getNodeMap().get(typeNode.getParent());
		if (pkgGNode == null) {
			throw new RuntimeException();
		}
		addConnection(pkgGNode, typeNode, typeDecl.getStartPosition());
		return super.visit(typeDecl);
	}

	private GNode insertTypeNode(TypeDeclaration typeDecl) {
		ITypeBinding rBinding = typeDecl.resolveBinding();
		String prjName = rBinding.getPackage().getJavaElement().getJavaProject().getElementName();
		String pkgName = rBinding.getPackage().getName();

		String typeName = typeDecl.getName().getFullyQualifiedName();
		String id = prjName + "." + pkgName + "." + typeName;
		GClassNode n = new GClassNode(id, typeName, prjName + "." + pkgName);
		n.setPkgName(pkgName);
		return addNode(n);
	}

	@Override
	public boolean visit(MethodDeclaration methodDecl) {
		if (methodDecl.getName().getFullyQualifiedName().equals(mNode.getName())){
			GMethodNode methodNode = (GMethodNode) insertMethodNode(methodDecl);
			GNode typeNode = GModelProvider.instance().getNodeMap().get(methodNode.getParent());
			if (typeNode == null) {
				throw new RuntimeException();
			}
			if (methodDecl != null) {
				
				System.out.println(methodDecl.getBody());
				System.out.println(methodDecl.getBody().statements());
				
				for(Object statement : methodDecl.getBody().statements()) {
					if(statement instanceof VariableDeclarationStatement) {
						System.out.println(((VariableDeclarationStatement) statement).fragments().toString());
	                    VariableDeclaration variableDecl = (VariableDeclaration) (((VariableDeclarationStatement) statement).fragments().get(0));
	                    GNode variableNode = (GNode) insertVariableNode(variableDecl);
	                    addConnection(methodNode, variableNode, variableDecl.getStartPosition());
					}
				}
            }
			addConnection(typeNode, methodNode, methodDecl.getStartPosition());
			return super.visit(methodDecl);
		}
		return false;
	}

	private GNode insertMethodNode(MethodDeclaration methodDecl) {
		IMethodBinding rBinding = methodDecl.resolveBinding();
		ITypeBinding typeBinding = rBinding.getDeclaringClass();
		String prjName = typeBinding.getPackage().getJavaElement().getJavaProject().getElementName();
		String pkgName = typeBinding.getPackage().getName();
		String className = typeBinding.getName();

		String methodName = methodDecl.getName().getFullyQualifiedName();
		String parent = prjName + "." + pkgName + "." + className;
		String id = parent + "." + methodName;
		GMethodNode n = new GMethodNode(id, methodName, parent);
		n.setPrjName(prjName).setPkgName(pkgName).setClassName(className);
		return addNode(n);
	}
	
	private GNode insertVariableNode(VariableDeclaration variableDecl) {
		String variableName = variableDecl.getName().getFullyQualifiedName();
		String parent = mNode.getPrjName() + "." + mNode.getPkgName() + "." + mNode.getClassName() + "." + mNode.getName();
		String id = parent + "." + variableName;
		GNode n = new GNode(id, variableName, parent);
		//n.setPrjName(prjName).setPkgName(pkgName).setClassName(className);
		return addNode(n);
	}

	private void addConnection(GNode srcNode, GNode dstNode, int offset) {
		String conId = srcNode.getId() + dstNode.getId();
		String conLabel = "offset: " + offset;
		GConnection con = new GConnection(conId, conLabel, srcNode, dstNode);
		GModelProvider.instance().getConnections().add(con);
		srcNode.getConnectedTo().add(dstNode);
	}

	private GNode addNode(GNode n) {
		GModelProvider.instance().getNodes().add(n);
		GModelProvider.instance().getNodeMap().put(n.getId(), n);
		return n;
	}
}
