package JADE_exemple_personnel;

import java.io.IOException;


import jade.core.Agent;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Google extends Agent {
		/** Cette méthode est appelé directement apèes la création de l'agent pour permettre
		  * l'initialisation et l'affectation des différents comportements à cet agent 
		  * */
	protected void setup() {
		System.out.println(getLocalName()+" STARTED");
		
		try {
			// Création de desciprion de l'agent [Vendeur]
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			// Enregistrement de la description de l'agent dans DF (Directory Facilitator)
			DFService.register(this, dfd);
			System.out.println(getLocalName()+" REGISTERED WITH THE DF");
			} catch (FIPAException e) {
			e.printStackTrace();
			}
			addBehaviour(new CyclicBehaviour(this) {
				
			public void action() {
				
			// Attente de message (de l'agent Acheteur)
			ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			if (msg != null) {				
			if (msg.getContent().equalsIgnoreCase("demande de listes des Liens"))
			{
				
			/* L'agent Vendeur a reçu un message de l'agent Acheteur 
			   contient une demande    de la liste des produits.
			   
			   L'agent Vendeur répond à l'agent Acheteur en lui envoyant 
			   un objet contenant la liste des produits.
			*/													
			ACLMessage reply = msg.createReply();
			
			/**
			 * Dans ce niveau vous pouvez préparer l'ensemble de données necessaire pour répondre
			 * à la demande de l'agent concerné
			 * (par exemple vous pouvez intérroger une basse de donnés et mettre le resultat dans l'objet obj)
			 * */
			Object[] obj={"http://www.google.com/search?q=syst%C3%A8mes+multi+agents&ie=utf-8&oe=utf-8&aq=t&rls=org.mozilla:fr:official&client=firefox-a"} ;
			try {
			
			// Le contenu de réponse est l'objet <obj>
			reply.setContentObject(obj);
			
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			
			// Envoyer la réponse à l'agent Acheteur	
			myAgent.send(reply);
			System.out.println(getLocalName()+" a envoyé la liste des Liens");
									
			} else if (msg.getContent().equalsIgnoreCase("Merci")){	
			
			/* l'agent Vendeur a reçu un message contient <Merci> de l'agent Acheteur
			   
			   L'agent Vendeur répond à l'agent Acheteur par <de rien>
			*/	
			ACLMessage reply = msg.createReply();
			
			// Le contenu de réponse est <de rien>
			reply.setContent(" de rien");
			
			// Envoyer la réponse à l'agent Acheteur		
			myAgent.send(reply);
			System.out.println(getLocalName()+" a répondu <de rien>");
			
			// Suppression de l'agent [Vendeur]
			doDelete();
			}
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
