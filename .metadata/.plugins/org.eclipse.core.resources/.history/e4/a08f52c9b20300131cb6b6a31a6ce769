package it.unitn.science.aose.salnitri.posWeatherForecast.behaviours;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

/**
 * This behaviour send back the weather forecast for a given position.
 * Position is retrieved from the message received.
 * 
 * @author Mattia Salnitri
 *
 */
public class ReplyWF extends Behaviour
{

	Boolean replied = false;
	
	@Override
	public void action() 
	{
		
		ACLMessage receivedMsg = myAgent.receive();
		if (receivedMsg!=null)
		{
			//System.out.println("Agent : " + myAgent.getAID().getName() +" received message: "+receivedMsg.getContent());
			replied = true;
			
			String content;
			
			//set up the weather forecast 
			if (receivedMsg.getContent().compareToIgnoreCase("Italy")==0)
				content="Rain";
			else
				content="Sun";
			
			//set up message parameters
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
