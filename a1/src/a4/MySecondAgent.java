package a4;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class MySecondAgent extends Agent 
{
	protected void setup()
	{
		
		OneShot osb = new OneShot();
		
		addBehaviour(osb);
	}
	
	private class OneShot extends OneShotBehaviour
	{

		@Override
		public void action() 
		{
			System.out.println("BAM!");
		}
		
	}

}
