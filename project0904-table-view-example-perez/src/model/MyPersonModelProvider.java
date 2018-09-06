package model;

import java.util.ArrayList;
import java.util.List;

public enum MyPersonModelProvider {
   INSTANCE;

   private List<MyPerson> persons;

   private MyPersonModelProvider() {
      persons = new ArrayList<MyPerson>();
      persons.add(new MyPerson("FirstNameA", "LastNameA", "Address1"));
      persons.add(new MyPerson("FirstNameA", "LastNameA", "Address1"));
      persons.add(new MyPerson("FirstNameA", "LastNameA", "Address1"));
      persons.add(new MyPerson("FirstNameA", "LastNameA", "Address1"));
   }


public List<MyPerson> getPersons() {
      return persons;
   }
}
