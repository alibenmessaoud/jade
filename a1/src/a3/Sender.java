package a3;

import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.*;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class Sender extends Agent {
	private Codec codec = new SLCodec();
	private Ontology onto = MyOntology.getInstance();
	
	@Override
	protected void setup() {
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(onto);
		
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(new AID("Receiver", AID.ISLOCALNAME));
		msg.setLanguage(codec.getName());
		msg.setOntology(onto.getName());
		
		Person paolo = new Person();
		paolo.setName("Paolo");
		paolo.setSurname("Giorgini");
		
		Person fabiano = new Person();
		fabiano.setName("Fabiano");
		fabiano.setSurname("Dalpiaz");
		fabiano.setEmployer(paolo);
		
		KnowPerson pred = new KnowPerson();
		pred.setPersonIKnow(fabiano);
		
		try {
			getContentManager().fillContent(msg, pred);
			send(msg);
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}