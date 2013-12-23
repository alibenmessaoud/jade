package it.unitn.science.aose.salnitri.posWeatherForecast.behaviours;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

/**
 * This behaviour receives the weather forecast
 * 
 * @author Mattia Salnitri
 *
 */
public class ReceiveWF extends Behaviour 
{
	Boolean received = false;
	
	@Override
	public void action() 
	{
		ACLMessage msg = myAgent.receive();
		if (msg!=null)
		{
			//System.out.println("Agent : " + myAgent.getAID().getName() +" received message: "+msg.getContent());
			//print out weather forecast
			System.out.println("Weather forecast: "+msg.getContent());
			received = true;
		}
		else
			block();
	}

	@Override
	public boolean done() 
	{
		return received;
	}
}
