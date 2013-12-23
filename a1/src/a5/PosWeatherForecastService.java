package a5;

 
import java.util.Vector;

import a5.behaviours.ReceivePrice;
import a5.behaviours.SelectBestService;
import a5.behaviours.SendMsg;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

/**
 * search through all weather forecast services (WFS) the less expensive, 
 * retrieve the position and ask its the weather forecast for the position
 * 
 * @author Mattia Salnitri
 *
 */
public class PosWeatherForecastService extends Agent 
{
	protected void setup()
	{
		DFAgentDescription[] result=null;
		
		//use df to search WF services
        try 
        {
        	//create the agent descriptor
    		DFAgentDescription dfd = new DFAgentDescription();
    		ServiceDescription sd  = new ServiceDescription();
    		//set up the parameters, ion this case the type of the service we want to retrieve
    		sd.setType( "WFS" );
    		dfd.addServices(sd);
    		//search 
    		result = DFService.search(this, dfd);
    		//if the result of the search is empty, rise an error
    		if (result==null || result.length==0)
    		{
    			System.err.println("no WFS available");
    			return;
    		}	
    		
   			//System.out.println("Search for WFS: " + result.length + " elements" );
    			   
        }
        catch (FIPAException fe) 
        { 
        	fe.printStackTrace(); 
        }
		
        
        //set up behaviours
		SequentialBehaviour sb = new SequentialBehaviour();
		//list of WFS with the prices
		Vector <WFServiceDescriptor> services = new Vector<WFServiceDescriptor>();
		
		//for every WFS retrieved, ask for the price offered
		for (int i=0; i<result.length; i++)
		{
			//System.out.println("Added requests for WFS: " + result[i].getName());
			//ask for the price
			sb.addSubBehaviour(new SendMsg("price?",result[i].getName()));
			//wait for a response, memorize the response in services vector
			sb.addSubBehaviour(new ReceivePrice(services));
		}

		//select best service behaviour
		sb.addSubBehaviour(new SelectBestService(services, sb));
		addBehaviour(sb);
		
	}
	
	protected void takeDown()
	{}

}
