package a7;
/**************************************************************   
     ParamAgent.java:  Retrieving parameters
 **************************************************************/   
     
     import jade.core.Agent;
    
     public class ParamAgent extends Agent 
     { 
        protected void setup() 
        { 
            Object[] args = getArguments();
            String s;
            if (args != null) {
                for (int i = 0; i<args.length; i++) {
                    s = (String) args[i];
                    System.out.println("p" + i + ": " + s);
                }
                
                // Extracting the integer.
                int i = Integer.parseInt( (String) args[0] );
                System.out.println("i*i= " + i*i);
            }
        }
     }