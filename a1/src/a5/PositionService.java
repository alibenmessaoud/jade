package a5;

 
import a5.behaviours.ReplyPos;
import a5.behaviours.SendMsg;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * wait a message, and send back a position
 * 
 * @author Mattia Salnitri
 *
 */
public class PositionService extends Agent 
{
	protected void setup()
	{
		SequentialBehaviour sb = new SequentialBehaviour();
		
		//wait for a request and send back the position
		sb.addSubBehaviour(new ReplyPos());
		
		addBehaviour(sb);		
	}
	
	protected void takeDown()
	{}

}
