package a5;

 
import jade.core.AID;
import jade.core.Agent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;


/**
 * Initiate agents
 * 
 * @author Mattia Salnitri
 *
 */
public class Initiator extends Agent 
{

	protected void setup()
	{
		//start agents
		System.out.println("Start services");
		createAgent("WFS1", "a5.WeatherForecastService");
		createAgent("WFS2", "a5.WeatherForecastService");
		createAgent("WFS3", "a5.WeatherForecastService");
		createAgent("PositionService", "a5.PositionService");
		createAgent("PosWeatherForecastService", "a5.PosWeatherForecastService");
	}
	
	private void createAgent(String name, String agentType)
	{
		//retrieve the container controller
    	AgentContainer c = getContainerController();
    	try {
    		//setup agent controller
    		AgentController a = c.createNewAgent( name, agentType, null );
    		//start agent
    		a.start();
    		System.out.println("Created: " + (new AID(name, AID.ISLOCALNAME)));
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
	}
	

}
