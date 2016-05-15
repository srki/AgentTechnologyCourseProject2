package com.ftn.informatika.agents.environment.messages;

import com.ftn.informatika.agents.environment.AgentsLocal;
import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.Agent;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * @author - Srđan Milaković
 */
@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/ExpiryQueue")
        }
)
public class MessageDispatcher implements MessageListener {

    @EJB
    private AgentsLocal agentsBean;

    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            try {
                Object object = ((ObjectMessage) message).getObject();
                if (object instanceof ACLMessage) {
                    ACLMessage aclMessage = ((ACLMessage) object);

                    aclMessage.getReceivers().forEach(receiver -> {
                        Agent agent = agentsBean.getAgent(receiver);
                        if (agent != null) {
                            agent.handleMessage(aclMessage);
                        }
                    });
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
