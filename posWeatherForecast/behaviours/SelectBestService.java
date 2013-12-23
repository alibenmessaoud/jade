package it.unitn.science.aose.salnitri.posWeatherForecast.behaviours;

import it.unitn.science.aose.salnitri.posWeatherForecast.WFServiceDescriptor;

import java.util.Iterator;
import java.util.Vector;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * This behaviour select the best service among ones contained in "Service" vector.
 * Add other Behaviours.  
 * 
 * @author Mattia Salnitri
 *
 */
public class SelectBestService extends OneShotBehaviour 
{
	Vector <WFServiceDescriptor> services;
	AID bestService;
	SequentialBehaviour sb;
	
	public SelectBestService(Vector <WFServiceDescriptor> services, SequentialBehaviour sb )
	{
		this.services=services;
		this.sb=sb;
		
	}

	@Override
	public void action() 
	{
		//select the best service
		int bestPrice=-1;
		Iterator<WFServiceDescriptor> serviceIt = services.iterator();
		while (serviceIt.hasNext())
		{
			WFServiceDescriptor wfsd = serviceIt.next();
			if (wfsd.getPrice()<bestPrice || bestPrice<0)
			{
				bestPrice=wfsd.getPrice();
				bestService=wfsd.getService();
			}
		}

		
		System.out.println("best price: " + bestPrice + ", by WFS: " + bestService.getLocalName());
		
		//once i chose the best service i retrieve the position and then i ask for the weather		
		sb.addSubBehaviour(new SendMsg("pos?", new AID("PositionService", AID.ISLOCALNAME)));
		sb.addSubBehaviour(new RequestWF(bestService));
		sb.addSubBehaviour(new ReceiveWF());
		


	}


}
