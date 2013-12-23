package it.unitn.science.aose.salnitri.posWeatherForecast.behaviours;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * This behaviour send a message to a given agent with a given content.
 * the content and the target are parameters of constructor.
 * 
 * @author Mattia Salnitri
 *
 */
public class SendMsg extends OneShotBehaviour 
{
	String content;
	AID receiver;
	public SendMsg(String content, AID receiver)
	{
		this.content=content;
		this.receiver=receiver;
	}
	
	@Override
	public void action() 
	{
		
		//set up message parameters
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(receiver);
		msg.setLanguage("English");
		msg.setOntology("Weather-forecast-ontology");
		msg.setContent(content);
		//send message
		myAgent.send(msg);
		
	}

}
