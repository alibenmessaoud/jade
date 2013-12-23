package a3;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Receiver extends Agent {
	private Codec codec = new SLCodec();
	private Ontology onto = MyOntology.getInstance();
	
	@Override
	protected void setup() {
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(onto);
		
		// Receive the message
		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchLanguage(codec.getName()),
				MessageTemplate.MatchOntology(onto.getName()) 
				);
		ACLMessage msg = blockingReceive(mt);
		if (msg.getPerformative()==ACLMessage.INFORM) {
			try {
				ContentElement ce = getContentManager().extractContent(msg);
				if (ce instanceof KnowPerson) {
					KnowPerson kp = (KnowPerson) ce;
					Person person = kp.getPersonIKnow();
					System.out.println("Agent " + 
							msg.getSender().getName() + 
							" knows " + person.getName() + 
							" " + person.getSurname());
					if (person.getEmployer()!=null) {
						Person employer = person.getEmployer();
						System.out.println(" whose employer is " + 
								employer.getName() +
								" " + employer.getSurname() +
								" ");
					}
				}
			} catch (UngroundedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CodecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OntologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
