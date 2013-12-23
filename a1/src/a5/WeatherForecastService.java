package a5;

 
import a5.behaviours.ReplyPrice;
import a5.behaviours.ReplyWF;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/**
 * register the service in DF, reply with the price, then reply with the weather forecast of a given position.
 * 
 * @author Mattia Salnitri
 *
 */
public class WeatherForecastService extends Agent 
{
	protected void setup()
	{
		//create the service descriptor
		ServiceDescription sd  = new ServiceDescription();
		//set up the type of the service
        sd.setType("WFS");
        //setup the name of the service
        sd.setName(getLocalName());
        register(sd);
		
        
		SequentialBehaviour sb = new SequentialBehaviour();
		
		//wait for a request and send back the price
		sb.addSubBehaviour(new ReplyPrice());
		
		//wait for another request and send back the weather forecast
		sb.addSubBehaviour(new ReplyWF());
		
		addBehaviour(sb);
	}
	
    /**
     * register the service to DF
     * 
     * @param sd service descriptor needed for the registration to DF
     */
    void register( ServiceDescription sd)
    {
    	//crate agent descriptor
        DFAgentDescription dfd = new DFAgentDescription();
        //set the name of the agent
        dfd.setName(getAID());
        //set up service descriptor to agent descriptor
        dfd.addServices(sd);

        try 
        {  
        	//register the services
            DFService.register(this, dfd );  
        }
        catch (FIPAException fe) 
        { 
        	fe.printStackTrace(); 
        }
    }
	
	protected void takeDown()
	{
		try 
		{ 
			DFService.deregister(this); }
        catch (Exception e) {}
	}

}
