package JADE_exemple_personnel;

import jade.core.Agent;
import jade.core.Runtime;
import jade.core.ProfileImpl;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.wrapper.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JTextArea;
import javax.swing.JButton;



import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class Maitre extends Agent {
			private JFrame jFrame = null;
			private JPanel jContentPane = null;
			private JPanel jPanel = null;
			private JPanel jPanel1 = null;
			private JPanel jPanel2 = null;
			private JTextArea jTextArea=null;
			private JButton jButton = null;
			private JScrollPane jScrollPane=null;
			private JLabel jLabel=null;
			
			/** Cette m�thode est appel� directement ap�es la cr�ation de l'agent pour permettre
			  * l'initialisation et l'affectation des diff�rents comportements � cet agent 
			  * */
			protected void setup() {
	    	getJFrame().setVisible(true);
	    	//jTextArea.append("Agent "+getLocalName()+" est lanc� "+"\n");
	    	System.out.println("Agent "+getLocalName()+" est lanc� ");
	    	try {
			
	    	// Cr�ation de desciprion de l'agent [Maitre]
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			
			// Enregistrement de la description de l'agent dans DF (Directory Facilitator)
			DFService.register(this, dfd);
			//jTextArea.append("Agent "+getLocalName()+" est enregistr� dans DF (Directory Facilitator) "+"\n");
			System.out.println("Agent "+getLocalName()+" est enregistr� dans DF (Directory Facilitator) ");
	    	} catch (FIPAException e) {
			e.printStackTrace();}
	    	
	    	addBehaviour(new CyclicBehaviour(this) {
	    		
			public void action() {
		
			// Attente de message (de l'agent Enregistreur)
			ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			if (msg != null) {				
			try {
			
				// Cr�ation de l'objet [obj] � partir de du message re�u(de l'agent Enregistreur)	
			Object[] obj=(Object[]) msg.getContentObject();
			jTextArea.append("Message re�u <la liste des liens>"+"\n");
			jTextArea.append("Contenu de message {"+"\n");
			for(int i=0;i<obj.length;i++){
			jTextArea.append((String) obj[i]+"\n");					
			}
			jTextArea.append("}"+"\n");
			jButton.setText("Terminer");
			} catch (UnreadableException e) {
			e.printStackTrace();}
			jTextArea.append("Fin de traitement"+"\n");									
			
			// Suppression de l'agent [Maitre]
			doDelete();
			}
			else {
		
			//Pendant que le message n'est pas encore arriv� le comportement est bloqu�
			block();
				}
			}
	    	});
	    	
			}
		
			protected void takeDown() {
					try {
					// Suppression de l'agent [Maitre] depuis le DF
					DFService.deregister(this);
					jTextArea.append("Agent "+getLocalName()+" est termin� et supprim� depuis DF (Directory Facilitator) "+"\n");
					System.out.println("Agent "+getLocalName()+" est termin� et supprim� depuis DF (Directory Facilitator) ");
					} catch (FIPAException e) {
					e.printStackTrace();}
					}
						
			public JFrame getJFrame() {
				if (jFrame == null) {
					jFrame = new JFrame();
					jFrame.setSize(new java.awt.Dimension(500,350));
					Dimension tailleEcran =Toolkit.getDefaultToolkit().getScreenSize();
					int largeurEcran = tailleEcran.width;
					int hauteurEcran = tailleEcran.height;
					jFrame.setLocation((largeurEcran-500)/2,(hauteurEcran-350)/2);
					jFrame.setTitle("Donner l'expression � rechercher.");
					jFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);					
					jFrame.setContentPane(getJContentPane());
				}
				return jFrame;
			}

			private JPanel getJContentPane() {
				if (jContentPane == null) {
					jContentPane = new JPanel();		
					jContentPane.setLayout(new BorderLayout());
					jContentPane.add(getJPanel2(), java.awt.BorderLayout.NORTH);
					jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
					jContentPane.add(getJPanel(), java.awt.BorderLayout.SOUTH);
					}
				return jContentPane;
				}

			private JPanel getJPanel() {
				if (jPanel == null) {
					FlowLayout flowLayout = new FlowLayout();
					flowLayout.setAlignment(java.awt.FlowLayout.CENTER);
					jPanel = new JPanel();
					jPanel.setLayout(flowLayout);
					jPanel.add(getJButton(), null);
					}
				return jPanel;
				}

			private JPanel getJPanel1() {
				if (jPanel1 == null) {
					jPanel1 = new JPanel();
					jPanel1.setLayout(new BorderLayout());
					jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
					}
				return jPanel1;
				}

			private JTextArea getJTextArea() {
				if (jTextArea == null) {
					jTextArea = new JTextArea();
					}
				return jTextArea;
				}

			private JButton getJButton() {
				if (jButton == null) {
					jButton = new JButton();
					jButton.setText("Chercher");
					
					/** L'objectif de ce button et de cr�er les deux agents Enregistreur et Google
					  * et pour cela on doit premi�rement r�cup�rer le conteneur en cours (Container) de Jade
					  * puis cr�er deux agents Enregistreur et Google pour lancer le processu d'�change de message
					  * et cette partie repr�sente la solution la plus robuste pour l'exploitation des agents  
					  * ansi que leurs service dans des classes java (Application, Applets, Servelets,etc...)
					  */
					jButton.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
						try {
							
						// R�cup�ration du conteneur (Main Container) en cours d'execution de Jade					
						Runtime rt = Runtime.instance();
						
						// Cr�ation du profil par d�fault
						ProfileImpl p = new ProfileImpl(false);
						AgentContainer container =rt.createAgentContainer(p);
						
						// Agent controleur pour permettre la cr�ation des agents 
						AgentController Agent=null;		
						
						/* Cr�ation de l'agent google*/
						Agent = container.createNewAgent("Google", "JADE_exemple_personnel.Google", null);
						
						// D�marrage de l'agent Google
						Agent.start();	
						jTextArea.append("Agent Google est lanc� "+"\n");
						
						/* Cr�ation de l'agent Enregistreur*/
						Agent = container.createNewAgent("Enregistreur", "JADE_exemple_personnel.Enregistreur", null);
				
						// D�marrage de l'agent Enregistreur
						Agent.start();
						jTextArea.append("Agent Enregistreur est lanc� "+"\n");
						} catch (Exception any) {
						any.printStackTrace();}
						}
					});
					}
				return jButton;
				}

			private JScrollPane getJScrollPane() {
				if (jScrollPane == null) {
					jScrollPane = new JScrollPane();
					jScrollPane.setViewportView(getJTextArea());
				}
				return jScrollPane;
				}

			private JPanel getJPanel2() {
				if (jPanel2 == null) {
					jLabel = new JLabel();
					jLabel.setText("Ecrire l'expression � rechercher");
					jLabel.setFont(new java.awt.Font("Perpetua", java.awt.Font.BOLD, 18));
					jPanel2 = new JPanel();
					jPanel2.setBackground(java.awt.SystemColor.info);
					jPanel2.add(jLabel, null);
				}
				return jPanel2;
				}
		
			public static void main(String[] args) {
				try {
					// R�cup�ration du conteneur (Main Container) en cours d'execution de Jade					
					Runtime rt = Runtime.instance();
				
					// Cr�ation du profil par d�fault
					ProfileImpl p = new ProfileImpl(false);
					AgentContainer container =rt.createAgentContainer(p); // get a container controller for creating new agents
				
					// Agent controleur pour permettre la cr�ation des agents 
					AgentController Agent=null;		
				
					/* Cr�ation de l'agent Portail
					   cette commande est �quivalente � la suivante: 
					   java jade.Boot Portail:JADE_exemple_personnel.Portail
					*/
					Agent = container.createNewAgent("Portail", "JADE_exemple_personnel.Portail", null);
				
					// D�marrage de l'agent Portail
					Agent.start();											
					} catch (Exception any) {
					any.printStackTrace();}				
				}
}
