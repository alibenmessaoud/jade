package a6;
import jade.core.AID;

import jade.core.Agent;

import jade.core.behaviours.*;

import jade.lang.acl.*;



public class Translator extends Agent {

	@Override	

	protected void setup() {

		addBehaviour(new Receive("ita"));

		addBehaviour(new Receive("eng"));

	}

	

	private class Receive extends CyclicBehaviour {

		private String from;

		private String to;

		

		public Receive (String from) {

			this.from = from;

			if (from.equals("ita")) {

				to = new String ("eng");

			}

			else to = new String ("ita");

		}

		@Override

		public void action() {

			MessageTemplate tpl = MessageTemplate.MatchLanguage(from);

			ACLMessage msg = receive(tpl);

			if (msg!=null) {

				System.out.println("Received " + msg.getContent());

				String toTranslate = msg.getContent();

				String replyString = lookUp(from,to,toTranslate);

				

				if (replyString.equals("")) {

					ACLMessage reply = new ACLMessage(ACLMessage.FAILURE);

					reply.addReceiver(msg.getSender());

					reply.setLanguage(to);

					reply.setContent("no traslation found");

					send(reply);

					return;

				}

				

				ACLMessage reply = new ACLMessage(ACLMessage.INFORM);

				reply.addReceiver(msg.getSender());

				reply.setLanguage(to);

				reply.setContent(replyString);

				send(reply);

				

			}

			else block();	

		}

		

		private String lookUp (String from, String to, String toTranslate) { 

			if (from.equals("ita")) {

				if (toTranslate.equals(new String("casa")))

					return new String("house");

				else if (toTranslate.equals(new String("ciao")))

					return new String("hello");

				else if (toTranslate.equals(new String("freddo")))

					return new String("cold");

			}

			else {

				if (toTranslate.equals(new String("house")))

					return new String("casa");

				else if (toTranslate.equals(new String("hello")))

					return new String("ciao");

				else if (toTranslate.equals(new String("cold")))

					return new String("freddo");



			}

			return new String("");

		}

		

	}

}






