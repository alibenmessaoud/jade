package a3;

import java.util.List;
import java.util.Set;

import jade.content.Predicate;

public class KnowPerson implements Predicate {

	private Person personIKnow;
	
	public KnowPerson() {
		personIKnow = null;
	}
	
	public void setPersonIKnow(Person pers) {
		personIKnow = pers;
	}
	
	public Person getPersonIKnow() {
		return personIKnow;
	}

}
