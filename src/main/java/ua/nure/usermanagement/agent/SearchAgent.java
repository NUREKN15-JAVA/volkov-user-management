package ua.nure.usermanagement.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import ua.nure.usermanagement.User;
import ua.nure.usermanagement.agent.behaviour.RequestServer;
import ua.nure.usermanagement.agent.behaviour.SearchRequestBehaviour;
import ua.nure.usermanagement.agent.exception.SearchException;
import ua.nure.usermanagement.database.DaoFactory;
import ua.nure.usermanagement.database.exception.DatabaseException;
import ua.nure.usermanagement.gui.SearchGui;

import java.util.Collection;

/**
 * A JADE agent, that works with search requests in user management system
 */
public class SearchAgent extends Agent {

    private AID[] aids = new AID[0];

    private SearchGui gui = null;

    /**
     * Prepares a search agent for execution. Also sets up GUI for this agent
     */
    @Override
    protected void setup() {
        super.setup();
        gui = new SearchGui(this);
        gui.setVisible(true);
        addBehaviour(new TickerBehaviour(this, 60000) {
            /**
             * A timed behaviour, that checks for all other search agents in the system
             */
            @Override
            protected void onTick() {
                DFAgentDescription description = new DFAgentDescription();
                description.setName(getAID());
                ServiceDescription serviceDescription = new ServiceDescription();
                serviceDescription.setName("JADE-Searching");
                serviceDescription.setType("searching");
                description.addServices(serviceDescription);
                try {
                    DFAgentDescription[] descriptions = DFService.search(myAgent, description);
                    AID[] newAids = new AID[descriptions.length];
                    for (int i = 0; i < newAids.length; i++) {
                        newAids[i] = descriptions[i].getName();
                    }
                    aids = newAids;
                } catch (FIPAException e) {
                    e.printStackTrace();
                }
            }
        });
        addBehaviour(new RequestServer());
        DFAgentDescription description = new DFAgentDescription();
        description.setName(getAID());
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setName("JADE-Searching");
        serviceDescription.setType("searching");
        description.addServices(serviceDescription);
        try {
            DFService.register(this, description);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        System.out.println("Agent " + getAID().getName() + " is ready! Awaiting orders!");
    }

    /**
     * Finishes execution of a search agent
     */
    @Override
    protected void takeDown() {
        super.takeDown();
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        gui.setVisible(false);
        gui.dispose();
        System.out.println("Agent " + getAID().getName() + " has finished working!");
    }

    /**
     *
     * @param firstName the first name of a queried user
     * @param lastName the last name of a queried user
     * @throws SearchException if some problem occurs during the search
     */
    public void search(String firstName, String lastName) throws SearchException {
        try {
            Collection<User> users = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
            if (users.size() > 0) {
                showUsers(users);
            } else {
                addBehaviour(new SearchRequestBehaviour(firstName, lastName, aids));
            }
        } catch (DatabaseException e) {
            throw new SearchException(e);
        }
    }

    /**
     * Sends a list of user into gui for visualisation
     * @param users a list of users for visualisation
     */
    public void showUsers(Collection<User> users) {
        gui.addUsers(users);
    }
}
