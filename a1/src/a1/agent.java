package a1;

import jade.core.*;


public class agent extends Agent {
	@Override
	protected void setup()
	{		
		Object []args = getArguments();
		System.out.println(getAID().getName()+"_______");		
		System.out.println("alo "+args[0].toString());
	}
	@Override
	protected void takeDown()
	{
		super.takeDown();
	}
}
