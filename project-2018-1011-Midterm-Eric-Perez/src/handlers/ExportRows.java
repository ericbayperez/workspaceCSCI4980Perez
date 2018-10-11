 
package handlers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import model.Person;
import model.ModelProvider;

public class ExportRows {
	@Execute
	public void execute(Shell shell) throws IOException {
		export();
		MessageDialog.openInformation(shell, "Export", "Saved output-midterm-1011-Eric-Perez.csv");
	}

	private void export() throws IOException {
		// TODO Auto-generated method stub
		List<Person> persons = ModelProvider.INSTANCE.getPersons();
		FileWriter writer = new FileWriter("/Users/ericperez/output-midterm-1011-Eric-Perez.csv"); 
		PrintWriter printWriter = new PrintWriter(writer);
		for(Person person : persons) {
			printWriter.write(person.getFirstName() + "," + person.getLastName() + "," + person.getPhone() + "," + person.getAddress() + System.lineSeparator());
		}
		writer.close();
		
	}
	
		
}