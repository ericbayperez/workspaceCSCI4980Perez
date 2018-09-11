package model;

public class MyPerson {
	
	private String  firstName;
	private String  lastName;
	private String  gender;
	private boolean married;

	public MyPerson() {}
	
	public MyPerson(String firstName, String lastName, String gender, boolean married) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.married = married;
	}
	public String getFirstName() {
		return firstName;
	}

	  public String getGender() {
	      return gender;
	   }

	   public String getLastName() {
	      return lastName;
	   }

	   public boolean isMarried() {
	      return married;
	   }

	   public void setFirstName(String firstName) {
	      this.firstName = firstName;
	   }

	   public void setGender(String gender) {
	      this.gender = gender;
	   }

	   public void setLastName(String lastName) {
	      this.lastName = lastName;
	   }

	   public void setMarried(boolean isMarried) {
	      this.married = isMarried;
	   }

	   @Override
	   public String toString() {
	      return firstName + " " + lastName;
	   }
}
