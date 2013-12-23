package a3;

import jade.content.Concept;
import jade.content.onto.annotations.*;

public class Person implements Concept {

	private String name;
	private String surname;
	private Person employer;
	
	@Slot(mandatory = true)
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getSurname() {
		return surname;
	}

	@Slot(mandatory = false)
	public void setEmployer(Person employer) {
		this.employer = employer;
	}
	public Person getEmployer() {
		return employer;
	}
}