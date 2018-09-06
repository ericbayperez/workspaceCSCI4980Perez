package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public enum MyPersonModelProvider {
   INSTANCE;

   private List<MyPerson> persons;

   private MyPersonModelProvider() {
	   persons = new ArrayList<MyPerson>();
	  
	   List<String> contents = readFile(getFilePath());
	   List<List<String>> tableContents = convertTableContents(contents);
	
		for (List<String> iList : tableContents) {
			System.out.println("[DBG] Show the elements:");
			persons.add(new MyPerson(iList.get(0), iList.get(1), iList.get(2)));
			}
   }


	public List<MyPerson> getMyPersons() {
      return persons;
   }


	public static List<List<String>> convertTableContents(List<String> contents) {
		List<List<String>> tableContents = new ArrayList<List<String>>();

		for (int i = 0; i < contents.size(); i++) {
			String line = contents.get(i);
			if (line == null || line.isEmpty()) {
				continue;
			}

			List<String> listElements = new ArrayList<String>();
			String[] splitedLine = line.split(":");

			for (int j = 0; j < splitedLine.length; j++) {
				String iElem = splitedLine[j].trim();
				listElements.add(iElem);
			}
			tableContents.add(listElements);
		}
		return tableContents;
	}

	public static List<String> readFile(String filePath) {
		List<String> contents = new ArrayList<String>();
		File file = new File(filePath);
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
	
	private String getFilePath() {
		return "/Users/ericperez/workspaceCSCI4980/workspaceCSCI4980Perez/project0906-tableview-perez/src/" + "inputdata.txt";
	}
}

