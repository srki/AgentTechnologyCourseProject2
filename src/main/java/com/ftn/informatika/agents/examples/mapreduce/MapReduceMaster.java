package com.ftn.informatika.agents.examples.mapreduce;

import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.environment.model.remote.RemoteAgent;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * MapReduce Master agent. Delegates tasks for Slaves, and collects results.
 *
 * @author Dragan Vidakovic
 */

@Stateful
@Remote(RemoteAgent.class)
public class MapReduceMaster extends Agent {
    private static final String DEFAULT_DOCUMENTS[] = {"srb.txt", "cro.txt", "eng.txt"};

    private int numberOfSlaves;
    private int delivered;
    private String[] documents;

    private Map<Character, Integer> mapReduce = new HashMap<>();

    @Override
    protected void handleRequest(ACLMessage message) {
        init(message.getContent());

        getLogManager().info("Request to MapReduceMaster: " + message.getContent());

        // Start agents
        ArrayList<AID> slaveAids = new ArrayList<>();

        for (int i = 0; i < numberOfSlaves; i++) {
            String slaveName = "MapReduceSlave" + i;
            AgentType slaveAt = new AgentType(MapReduceSlave.class);
            AID slaveAid = getAgentManager().runAgent(slaveAt, slaveName);
            slaveAids.add(slaveAid);
        }

        // Send messages
        for (int i = 0; i < numberOfSlaves; i++) {
            ACLMessage msg = new ACLMessage();
            msg.setPerformative(ACLMessage.Performative.REQUEST);
            msg.setSender(aid);
            msg.getReceivers().add(slaveAids.get(i));
            msg.setContent(documents[i]);
            getMessageManager().sendMessage(msg);
        }
    }

    @Override
    protected void handleInform(ACLMessage message) {
        delivered++;
        String senderName = message.getSender().getName();
        getLogManager().info("Inform to MapReduceMaster from " + senderName + ": " + message.getContent());

        parseResponse(message.getContent());

        if (delivered == numberOfSlaves) {
            getLogManager().info("Total statistics: " + formStatistics());
        }
    }

    private void init(String content) {
        documents = (content == null || content.length() == 0) ? DEFAULT_DOCUMENTS : content.split(",");
        numberOfSlaves = documents.length;
        delivered = 0;
        mapReduce.clear();
    }

    private void parseResponse(String input) {
        String splits[] = input.split("\n");
        for (int i = 1; i < splits.length; i++) {
            Character c = splits[i].charAt(0);
            int count = Integer.parseInt(splits[i].split(":")[1]);

            if (mapReduce.containsKey(c)) {
                mapReduce.put(c, mapReduce.get(c) + count);
            } else {
                mapReduce.put(c, count);
            }
        }
    }

    private String formStatistics() {
        String retVal = "MapReduce:";
        for (Map.Entry<Character, Integer> entry : mapReduce.entrySet()) {
            retVal += "\n" + entry.getKey() + ":" + entry.getValue();
        }
        return retVal;
    }
}
