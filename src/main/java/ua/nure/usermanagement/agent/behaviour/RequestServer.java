package ua.nure.usermanagement.agent.behaviour;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ua.nure.usermanagement.User;
import ua.nure.usermanagement.agent.SearchAgent;
import ua.nure.usermanagement.database.DaoFactory;
import ua.nure.usermanagement.database.exception.DatabaseException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

/**
 * A cyclic behaviour for a search agent. Determines a process of receiving a message
 * and processing it according to the type of it.
 */
public class RequestServer extends CyclicBehaviour {
    /**
     * Defines different types of processing, that happen based on the messages type
     */
    public void action() {
        ACLMessage message = myAgent.receive();
        if (message == null) {
            block();
        } else {
            if (message.getPerformative() == ACLMessage.REQUEST) {
                myAgent.send(createReply(message));
            } else if (message.getPerformative() == ACLMessage.INFORM) {
                Collection<User> users = parseMessage(message);
                ((SearchAgent) myAgent).showUsers(users);
            }
        }
    }

    /**
     * A part of message processing, that deals with search responses from other search agents
     *
     * @param message received message
     * @return Returns a list of user data, that has been sent from another agent
     */
    private Collection<User> parseMessage(ACLMessage message) {
        Collection<User> users = new ArrayList<>();
        String content = message.getContent();
        if (content != null) {
            StringTokenizer entryTokenizer = new StringTokenizer(content, ";");
            while (entryTokenizer.hasMoreTokens()) {
                StringTokenizer dataTokenizer = new StringTokenizer(entryTokenizer.nextToken(), ",");
                users.add(new User(new Long(dataTokenizer.nextToken()), dataTokenizer.nextToken(), dataTokenizer.nextToken()));
            }
        }
        return users;
    }

    /**
     * A part of message processing, that deals with search responses from other search agents
     *
     * @param message received message
     * @return Returns a reply for a request, that consists of formatted data about requested users
     */
    private ACLMessage createReply(ACLMessage message) {
        ACLMessage reply = message.createReply();
        String content = message.getContent();
        StringTokenizer tokenizer = new StringTokenizer(content, ",");
        if (tokenizer.countTokens() == 2) {
            String firstName = tokenizer.nextToken();
            String lastName = tokenizer.nextToken();
            Collection<User> collection = null;
            try {
                collection = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
            } catch (DatabaseException e) {
                e.printStackTrace();
                collection = new ArrayList<>();
            }
            StringBuilder builder = new StringBuilder();
            for (User user : collection) {
                builder.append(user.getId()).append(",");
                builder.append(user.getFirstName()).append(",");
                builder.append(user.getLastName()).append(";");
            }
            reply.setContent(builder.toString());
        }
        return reply;
    }
}
