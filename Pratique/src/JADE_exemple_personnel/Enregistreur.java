package JADE_exemple_personnel;

import java.io.IOException;
import jade.core.Agent;
import jade.core.AID;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class Enregistreur extends Agent {
	
	// Représent l'objet à envoyer dans le message vers l'agent Portail
	private Object[] obj=null;
	
	/** Cette méthode est appelé directement apèes la création de l'agent pour permettre
	  * l'initialisation et l'affectation des différents comportements à cet agent 
	  * */
	protected void setup() {
		System.out.println(getLocalName()+" STARTED");
		
			try {
			// Création de desciprion de l'agent [Acheteur]
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			// Enregistrement de la description de l'agent dans DF (Directory Facilitator)
			DFService.register(this, dfd);
			System.out.println(getLocalName()+" REGISTERED WITH THE DF");
			} catch (FIPAException e) {
			e.printStackTrace();
			}
			/* Préparation du message à envoyer vers l'agent Vendeur
			   ce message contient la demande de la liste de produits
			*/
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			
			// Remplissage de contenue du message
			msg.setContent("demande de listes des Liens");
			 
			// Préciser les agents destinataires du message qui est l'agent Vendeur dans ce cas
			msg.addReceiver(new AID("Google", AID.ISLOCALNAME));
			
			// Envoyer le message à l'agent Vendeur	
			send(msg);
			System.out.println(getLocalName()+" demande la liste des Liens"); 
	
			addBehaviour(new CyclicBehaviour(this) {
			public void action() {
			
			// Attente de message (de l'agent Vendeur)
			ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			if (msg != null) {				
									
			try {	
				
			// L'agent Acheteur répond à l'agent Vendeur <Merci> 	
			ACLMessage reply = msg.createReply();
			
			// Le contenu de réponse est <Merci>
			reply.setContent("Merci");
			// Envoyer la réponse à l'agent Vendeur		
			myAgent.send(reply);
			System.out.println(myAgent.getLocalName()+" a envoyé <merci>");		
			
			
			
			// Agent Acheteur transmet le message (objet obj) à l'agent Portail				
			obj=(Object[]) msg.getContentObject();				
			ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
			try {
			msg1.setContentObject(obj);
			msg1.addReceiver(new AID("Maitre", AID.ISLOCALNAME));
			send(msg1);	
			} catch (IOException e) {				
			e.printStackTrace();
			}				
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Les liens sont: ");
			for(int i=0;i<obj.length;i++) {
				System.out.println(obj[i]);
			}
			// Suppression de l'agent [Acheteur]
			doDelete();					
			}
			else {
				//Pendant que le message n'est pas encore arrivé le comportement est bloqué
				block();
			}
			}
		});
	}

	protected void takeDown() {
		// Suppression de l'agent [Acheteur] depuis le DF
		try {
			DFService.deregister(this);
			System.out.println(getLocalName()+" DEREGISTERED WITH THE DF");
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}
}
