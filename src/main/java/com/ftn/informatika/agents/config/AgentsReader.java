package com.ftn.informatika.agents.config;

import com.ftn.informatika.agents.environment.model.AgentType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dragan Vidakovic
 * @author Srđan Milaković
 */
public class AgentsReader {

    private static final String FILE_NAME = "agents.txt";
    private static final String SEPARATOR = ",";

    public static List<AgentType> getAgentsList() {
        URL url = AgentsReader.class.getResource("/" + FILE_NAME);

        List<AgentType> retVal = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] agent = line.split(SEPARATOR);
                if (agent.length != 2) {
                    System.err.println("Error in [" + FILE_NAME + "] line: " + line);
                }
                retVal.add(new AgentType(agent[1].trim(), agent[0].trim()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retVal;
    }

}

