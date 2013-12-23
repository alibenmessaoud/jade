package a5.behaviours;


import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class ReplyPos extends Behaviour 
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
			
			String content="Italy";
			
			ACLMessage replyMsg = new ACLMessage(ACLMessage.INFORM);
			//msg.addReceiver(new AID(target, AID.ISLOCALNAME));
			replyMsg.addReceiver(receivedMsg.getSender());
			replyMsg.setLanguage("English");
			replyMsg.setOntology("Weather-forecast-ontology");
			replyMsg.setContent(content);
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
