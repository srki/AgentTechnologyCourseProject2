package com.ftn.informatika.agents.examples.mapreduce;

import com.ftn.informatika.agents.environment.AgentsRemote;
import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.AID;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.AgentType;
import com.ftn.informatika.agents.environment.model.remote.RemoteAgent;
import com.ftn.informatika.agents.environment.util.factory.ManagerFactory;

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

    private int NUMBER_OF_SLAVES = 3;
    private int DELIVERED = 0;
    private String documents[] = {"srb.txt", "cro.txt", "eng.txt"};
    private Map<Character, Integer> mapReduce = new HashMap<Character, Integer>();

    @Override
    protected boolean handleRequest(ACLMessage message) {
        resetValues();

        getLogManager().info("Request to MapReduceMaster: " + message.getContent());

        // start agents
        AgentsRemote agm = ManagerFactory.getAgentManager();
        ArrayList<AID> slaveAids = new ArrayList<AID>();

        for (int i = 0; i < NUMBER_OF_SLAVES; i++) {
            String slaveName = "MapReduceSlave_" + documents[i];
            AgentType slaveAt = new AgentType(MapReduceSlave.class);
            AID slaveAid = agm.runAgent(slaveAt, slaveName);
            slaveAids.add(slaveAid);
        }

        // send messages
        for (int i = 0; i < NUMBER_OF_SLAVES; i++) {
            ACLMessage msg = new ACLMessage();
            msg.setPerformative(ACLMessage.Performative.REQUEST);
            msg.setSender(aid);
            msg.getReceivers().add(slaveAids.get(i));
            msg.setContent(documents[i]);
            getMessageManager().sendMessage(msg);
        }

        return true;
    }

    @Override
    protected boolean handleInform(ACLMessage message) {
        DELIVERED++;
        String senderName = message.getSender().getName();
        getLogManager().info("Inform to MapReduceMaster from " + senderName + ": " + message.getContent());

        parseResponse(message.getContent());

        if (DELIVERED == NUMBER_OF_SLAVES) {
            getLogManager().info("Total statistics: " + formStatistics());
        }

        return true;
    }

    private void resetValues() {
        NUMBER_OF_SLAVES = 3;
        DELIVERED = 0;
        mapReduce.clear();
    }

    private void parseResponse(String input) {
        String splits[] = input.split("\n");
        for (int i = 1; i < splits.length; i++) {
            Character c = splits[i].charAt(0);
            int count = Integer.parseInt(splits[i].split(":")[1]);

            if (mapReduce.containsKey(c)) {
                mapReduce.put(c, mapReduce.get(c) + 1);
            } else {
                mapReduce.put(c, 1);
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
