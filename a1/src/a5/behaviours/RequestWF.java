package a5.behaviours;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

/**
 * This behaviour request the weather forecast of a certain position
 * 
 * @author Mattia Salnitri
 *
 */
public class RequestWF extends Behaviour 
{
	Boolean replied=false;
	AID bestService;
	
	
	public RequestWF(AID bestService)
	{
		this.bestService=bestService;
	}
	
	@Override
	public void action() 
	{
		ACLMessage receivedMsg = myAgent.receive();
		if (receivedMsg!=null)
		{
			//System.out.println("Agent : " + myAgent.getAID().getName() +" received message: "+receivedMsg.getContent());
			replied = true;
			
			//position
			String content=receivedMsg.getContent();
			
			//set up message parameters
			ACLMessage replyMsg = new ACLMessage(ACLMessage.INFORM);
			replyMsg.addReceiver(bestService);
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
