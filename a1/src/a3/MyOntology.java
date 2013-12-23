package a3;

import jade.content.onto.*;

public class MyOntology extends BeanOntology {

	private static Ontology theInstance = new MyOntology("Person");
	
	public static Ontology getInstance() {
		return theInstance;
	}
	
	public MyOntology(String name) {
		super(name, BasicOntology.getInstance());
		try {
			add(Person.class);
			add(KnowPerson.class);
		} catch (BeanOntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
