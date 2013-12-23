package it.unitn.science.aose.salnitri.posWeatherForecast.behaviours;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

/**
 * This behaviour send back the price of a weather forecast
 * 
 * @author Mattia Salnitri
 *
 */
public class ReplyPrice extends Behaviour 
{
	Boolean replied=false;

	@Override
	public void action() 
	{
		ACLMessage receivedMsg = myAgent.receive();
		if (receivedMsg!=null)
		{
			//System.out.println("Agent : " + myAgent.getAID().getName() +" received message: "+receivedMsg.getContent());
			replied = true;
			
			String content;
			
			//set up the price 
			if (myAgent.getLocalName().compareToIgnoreCase("WFS1")==0)
				content="10";
			else if (myAgent.getLocalName().compareToIgnoreCase("WFS2")==0)
				content="20";
			else
				content="30";
			
			//set up parameters message
			ACLMessage replyMsg = new ACLMessage(ACLMessage.INFORM);
			replyMsg.addReceiver(receivedMsg.getSender());
			replyMsg.setLanguage("English");
			replyMsg.setOntology("Weather-forecast-ontology");
			replyMsg.setContent(content);
			//send message
			myAgent.send(replyMsg);
			
		}
		else
			block();

	}

	@Override
	public boolean done() 
	{
		return replied;
	}

}
