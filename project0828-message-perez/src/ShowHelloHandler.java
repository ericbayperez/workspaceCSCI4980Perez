import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class ShowHelloHandler {
	
	@Execute
	public void execute(Shell shell) {
		MessageDialog.openInformation(shell, "Title", "Hello " + testReadFile());
	}

	public String testReadFile() {
	      // A format of file "config.txt" consists of a key-value pair.
	      List<String> contents = readFile("/Users/ericperez/workspaceCSCI4980/workspaceCSCI4980Perez/project-20180913-Q1-Eric-Perez/src/config.txt");
	      for (int i = 0; i < contents.size(); i++) {
	         String line = contents.get(i);
	         System.out.println("[DBG] Line " + i + " - " + line);

	         String name = line.split(":")[1].trim();
	         return name;
	      }
	      return null;
	   }

	   public List<String> readFile(String filePath) {
		  File file = new File(filePath);
	      List<String> contents = new ArrayList<String>();
	      Scanner scanner = null;
	      try {
	         scanner = new Scanner(file);
	         while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            contents.add(line);
	         }
	      } catch (FileNotFoundException e) {
	         e.printStackTrace();
	      } finally {
	         if (scanner != null)
	            scanner.close();
	      }
	      return contents;
	   }
}