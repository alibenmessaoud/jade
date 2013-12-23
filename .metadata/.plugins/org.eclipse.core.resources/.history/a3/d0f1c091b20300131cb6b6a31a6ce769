package it.unitn.science.aose.salnitri.posWeatherForecast.behaviours;

import it.unitn.science.aose.salnitri.posWeatherForecast.WFServiceDescriptor;

import java.util.Vector;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

/**
 * This behaviour receives a price
 * and add reference to service and the price proposed to the list of all WFS  
 * 
 * @author Mattia Salnitri
 *
 */
public class ReceivePrice extends Behaviour 
{

	Boolean received=false;
	
	Vector <WFServiceDescriptor> services;
	
	public ReceivePrice(Vector <WFServiceDescriptor> services)
	{
		this.services=services;
	}
	
	@Override
	public void action() 
	{
		ACLMessage msg = myAgent.receive();
		if (msg!=null)
		{
			//System.out.println("Agent : " + myAgent.getAID().getName() +" received message: "+msg.getContent());
			//add service to list of all WFS
			services.add(new WFServiceDescriptor(msg.getSender(), Integer.parseInt(msg.getContent())));
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
