package a2;



import java.util.Date;

import java.util.Random;



import jade.core.Agent;

import jade.core.behaviours.CyclicBehaviour;

import jade.domain.DFService;

import jade.domain.FIPAException;

import jade.domain.FIPAAgentManagement.DFAgentDescription;

import jade.domain.FIPAAgentManagement.Property;

import jade.domain.FIPAAgentManagement.ServiceDescription;

import jade.lang.acl.ACLMessage;

import jade.lang.acl.MessageTemplate;





public class WeatherProvider extends Agent {



	int failurePercentage = 40;

	

	@Override

	protected void setup() {

		Object [] params = getArguments();

		if (params!=null)

			if (params.length==1)

				failurePercentage = Integer.parseInt(params[0].toString());

		

		DFAgentDescription descr = new DFAgentDescription();

		ServiceDescription sd = new ServiceDescription();

		sd.setName(getName());

		sd.addProperties(new Property(new String("failurePercentage"), failurePercentage));

		sd.setType("Forecast");

		descr.addServices(sd);

		try {

			DFService.register(this, descr);

		} catch (FIPAException e) {

			System.out.println("Cannot register my service");

			e.printStackTrace();

			doDelete();

		}

		

		addBehaviour(new ForecastService());

		

	}

	

	class ForecastService extends CyclicBehaviour {



		@Override

		public void action() {

			MessageTemplate tpl = MessageTemplate.MatchOntology("Forecast");

			tpl = MessageTemplate.and(tpl, MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

			

			ACLMessage msg = blockingReceive(tpl);

			if (msg!=null) {

				int luckyNr = (int) (Math.random() * 100);

				ACLMessage reply;

				if (luckyNr < failurePercentage) {

					reply = new ACLMessage(ACLMessage.FAILURE);

				}

				else {

					reply = new ACLMessage(ACLMessage.INFORM);

					if (Math.random()>0.5)

						reply.setContent("Fair");

					else reply.setContent("Rainy");

				}

				reply.setOntology("Forecast");

				reply.addReceiver(msg.getSender());

				send(reply);

			}

		}



	}

}

