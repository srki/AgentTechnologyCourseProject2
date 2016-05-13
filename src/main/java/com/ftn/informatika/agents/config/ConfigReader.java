package com.ftn.informatika.agents.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dragan Vidakovic
 */
public class ConfigReader {

    private static final String FILE_NAME = "agents.txt";

    public static List<String> getAgentsList()
    {
        URL url = ConfigReader.class.getResource("/" + FILE_NAME);

        List<String> retVal = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                Collections.addAll(retVal, line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retVal;
    }

}

