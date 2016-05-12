package com.ftn.informatika.agents.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Dragan Vidakovic
 */
public class ConfigReader {

    public List<String> getAgentsList()
    {
        String filePath = "src/main/java/com/ftn/informatika/agents/config/agents.txt";

        List<String> retVal = new ArrayList<String>();
        Scanner input = null;
        try
        {
            input = new Scanner(new File(filePath));
            String agents[] = input.nextLine().split(",");
            for (int i=0; i<agents.length; i++)
            {
                retVal.add(agents[i].trim());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return retVal;
    }

}

