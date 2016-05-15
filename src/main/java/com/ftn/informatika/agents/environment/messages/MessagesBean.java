package com.ftn.informatika.agents.environment.messages;

import com.ftn.informatika.agents.clustering.config.ConfigurationLocal;
import com.ftn.informatika.agents.environment.messages.http.MessageReceiverRequester;
import com.ftn.informatika.agents.environment.model.ACLMessage;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class MessagesBean implements MessagesLocal, MessagesRemote {

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "java:/jms/queue/ExpiryQueue")
    private Queue queue;

    @EJB
    private ConfigurationLocal configurationBean;

    @Override
    public void sendMessage(ACLMessage message) {
        sendJmsMessage(message);
        message.getReceivers().forEach(receiver -> {
            if (receiver != null && !configurationBean.getAgentCenter().equals(receiver.getHost())) {
                new MessageReceiverRequester(receiver.getHost().getAddress()).receiveMessage(message);
            }
        });
    }

    @Override
    public List<String> getPerformatives() {
        List<String> performatives = new ArrayList<>();
        for (ACLMessage.Performative performative : ACLMessage.Performative.values()) {
            performatives.add(performative.name());
        }
        return performatives;
    }

    @Override
    public void receiveMessage(ACLMessage message) {
        sendJmsMessage(message);
    }

    private void sendJmsMessage(ACLMessage message) {
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);

            ObjectMessage objectMessage = session.createObjectMessage();
            objectMessage.setObject(message);
            producer.send(objectMessage);

            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}
