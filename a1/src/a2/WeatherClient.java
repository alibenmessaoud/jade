package a2;

import java.util.*;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Property;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class WeatherClient extends Agent {
	@Override
	protected void setup() {

		addBehaviour(new RequestWeather());
		
	}
	
	class RequestWeather extends OneShotBehaviour {

		@Override
		public void action() {
			DFAgentDescription dfd = new DFAgentDescription();
	        ServiceDescription sd  = new ServiceDescription();
	        sd.setType("Forecast");
	        dfd.addServices(sd);
	        
	        try {
				DFAgentDescription[] result = DFService.search(myAgent, dfd);
				TreeMap<Integer,AID> ht = new TreeMap<Integer,AID>();
				
				for (int i=0;i<result.length;i++) {
					Iterator<ServiceDescription> it = result[i].getAllServices();
					while (it.hasNext()) {
						ServiceDescription sdprov = it.next();
						Iterator<Property> prp = sdprov.getAllProperties();
						while (prp.hasNext()) {
							Property prop = prp.next();
							if (prop.getName().equals(new String("failurePercentage"))) {
								String perc = prop.getValue().toString();
								int percent = Integer.parseInt(perc);
								System.out.println("Provider: " + sdprov.getName() + " failure prob: " + percent);
								ht.put(percent, result[i].getName());
							}		
						}
					}
				}
					
				
				Iterator<AID> provs = ht.values().iterator();
				while (provs.hasNext()) {
					ACLMessage msg1 = new ACLMessage(ACLMessage.REQUEST);
					msg1.addReceiver(provs.next());
					msg1.setOntology("Forecast");
					msg1.setContent("Tell me the weather");
					send(msg1);
					MessageTemplate tpl = MessageTemplate.MatchOntology("Forecast");
					ACLMessage msg2 = blockingReceive(tpl);
					if (msg2.getPerformative()==ACLMessage.FAILURE) {
						System.out.println("Provider " + msg2.getSender().toString() + " has no information.");
						continue;
					}
					else {
						System.out.println("Provider " + msg2.getSender().toString() + " says that the weather is " + msg2.getContent() + ".");
						doDelete();
						return;
					}

				}
				System.out.println("No available provider!");
				doDelete();
				
			} catch (FIPAException e) {
				System.out.println("No provider found");
				e.printStackTrace();
				doDelete();
			}

			
		}
		
	}
	
}
