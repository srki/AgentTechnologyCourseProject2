package com.ftn.informatika.agents.examples.mapreduce;

import com.ftn.informatika.agents.clustering.startup.config.AgentsReader;
import com.ftn.informatika.agents.environment.model.ACLMessage;
import com.ftn.informatika.agents.environment.model.Agent;
import com.ftn.informatika.agents.environment.model.remote.RemoteAgent;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Map Reduce Slave agent. Executes task delegated by Master agent, and returns results.
 *
 * @author Dragan Vidakovic
 */

@Stateful
@Remote(RemoteAgent.class)
public class MapReduceSlave extends Agent {
    private static final String HTTP = "http";

    @Override
    protected void handleRequest(ACLMessage message) {
        String path = message.getContent().trim();
        getLogManager().info(aid.getName() + ": " + path);

        Map<Character, Integer> mapReduce = new HashMap<>();
        try {
            URL url;
            if (path.startsWith(HTTP)) {
                url = new URL(path);
            } else {
                url = AgentsReader.class.getResource("/" + path);
            }
            fillMap(url, mapReduce);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        ACLMessage reply = new ACLMessage();
        reply.setPerformative(ACLMessage.Performative.INFORM);
        reply.setSender(aid);
        reply.getReceivers().add(message.getSender());
        reply.setContent(formReply(mapReduce));
        getMessageManager().sendMessage(reply);
    }

    private void fillMap(URL url, Map<Character, Integer> mapReduce) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                line = line.toLowerCase();
                Charset.forName("UTF-8").encode(line);
                for (int i = 0; i < line.length(); i++) {
                    Character c = line.charAt(i);
                    if (Character.isLetterOrDigit(c)) {
                        if (mapReduce.containsKey(c)) {
                            mapReduce.put(c, mapReduce.get(c) + 1);
                        } else {
                            mapReduce.put(c, 1);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formReply(Map<Character, Integer> mapReduce) {
        String retVal = "Statistics:";
        for (Map.Entry<Character, Integer> entry : mapReduce.entrySet()) {
            retVal += "\n" + entry.getKey() + ":" + entry.getValue();
        }
        return retVal;
    }
}
