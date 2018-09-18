 

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import view.SimpleView20180913Q1EricPerez;

public class Reverse {
	@Inject
	private EPartService epartService;

	@Execute
	public void execute() {
		System.out.println("[DBG] ViewPopupHandler");
		MPart findPart = epartService.findPart(SimpleView20180913Q1EricPerez.VIEW_ID);
		Object findPartObj = findPart.getObject();

		if (findPartObj instanceof SimpleView20180913Q1EricPerez) {
			SimpleView20180913Q1EricPerez v = (SimpleView20180913Q1EricPerez) findPartObj;
			v.setText(reverseString(v.getText()));
		}
	}
	
	private String reverseString(String input) {
		return new StringBuilder(input).reverse().toString();
	}
}