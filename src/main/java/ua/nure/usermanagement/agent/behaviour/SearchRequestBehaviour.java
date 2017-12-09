package ua.nure.usermanagement.agent.behaviour;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SearchRequestBehaviour extends Behaviour {

    private AID[] aids;
    private String lastName;
    private String firstName;

    public SearchRequestBehaviour(String firstName, String lastName, AID[] aids) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.aids = aids;
    }

    @Override
    public void action() {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.setContent(firstName + "," + lastName);
        for (AID aid : aids) {
            message.addReceiver(aid);
        }
        myAgent.send(message);
    }

    @Override
    public boolean done() {
        return true;
    }
}
