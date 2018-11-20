 
package handler;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import analysis.ProjectAnalyzerClassEx;
import graph.builder.GModelBuilder;
import graph.model.GNode;
import util.UtilMsg;
import util.UtilNode;
import view.SimpleZestGraphView;

public class PerezMenuItemHandler {
	@Execute
	public void execute(EPartService service) throws CoreException{
		System.out.println("[DBG] PerezMenuItemHandler");
	      UtilMsg.openWarning("This is as far as I made it. I tried a lot of stuff, but was never able to change the background color.");
	       
	      MPart findPart = service.findPart(SimpleZestGraphView.SIMPLEZESTVIEW);
	      if (findPart != null && findPart.getObject() instanceof SimpleZestGraphView) {
	         for (GNode node : GModelBuilder.instance().getNodes()) {
	        	 
	         }
	      }
	}
		
}