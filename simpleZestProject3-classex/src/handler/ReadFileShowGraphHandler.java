
package handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import graph.builder.GModelBuilder;
import util.UtilMsg;
import view.SimpleZestView;

public class ReadFileShowGraphHandler {
   @Inject
   EPartService service;

   @Execute
   public void execute() {
      SimpleZestView zestView = (SimpleZestView) service.findPart(SimpleZestView.SIMPLEZESTVIEW_ID).getObject();
      System.out.println("File Opened");
      UtilMsg.openWarning("Opened File and Read Data");
      //TODO: Class Exercise
      zestView.update(GModelBuilder.instance().buildByGraphInfoFile().getNodes());
   }
}