package model;

import java.util.ArrayList;
import java.util.List;

import util.UtilFile;

public enum ModelProvider {
	// INSTANCE;
	INSTANCE(getFilePath()); // Call a constructor with a parameter. 

	private List<Person> persons;

        // Load hard-coded data sets. 
	private ModelProvider() {
	}

        // Load the data sets from a file dynamically. 
	private ModelProvider(String inputdata) {
		List<String> contents = UtilFile.readFile(inputdata);
		List<List<String>> tableContents = UtilFile.convertTableContents(contents);

		persons = new ArrayList<Person>();
		for (List<String> iList : tableContents) {                        
			persons.add(new Person(iList.get(0), iList.get(1), iList.get(2), iList.get(3)));
		}
	}

	private static String getFilePath() {
		return "/Users/ericperez/workspaceCSCI4980/workspaceCSCI4980Perez/project-2018-1011-Midterm-Eric-Perez/src/inputdata-midterm-1011.txt";
	}

	public List<Person> getPersons() {
		return persons;
	}
}