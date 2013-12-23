package a2;

import jade.core.Agent;

import jade.domain.DFService;

import jade.domain.FIPAException;

import jade.domain.FIPAAgentManagement.DFAgentDescription;

import jade.domain.FIPAAgentManagement.ServiceDescription;

public class DFTester extends Agent {

	@Override
	protected void setup() {

		DFAgentDescription agDescr = new DFAgentDescription();

		ServiceDescription service = new ServiceDescription();

		service.setType("ExampleType");

		service.setName("serviceName");

		agDescr.addServices(service);

		try {

			DFService.register(this, agDescr);

		} catch (FIPAException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		DFAgentDescription agDescr2 = new DFAgentDescription();

		ServiceDescription service2 = new ServiceDescription();

		service2.setType("ExampleType");

		agDescr2.addServices(service2);

		try {

			DFAgentDescription[] descrs = DFService.search(this, agDescr2);

			for (int i = 0; i < descrs.length; i++)

				System.out.println(descrs[i].getName());

		} catch (FIPAException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

}