package com.ftn.informatika.agents.environment;

import javax.ejb.Local;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Local
public interface MessagesLocal extends MessagesRemote {
    List<String> getPerformatives();
}
