package com.ftn.informatika.agents.web_client.util.stream;

import com.ftn.informatika.agents.web_client.service.web_socket.beans.SessionsDbLocal;
import com.ftn.informatika.agents.web_client.service.web_socket.model.WebSocketPacket;

import javax.ejb.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class StreamBean implements StreamLocal {

    private List<MessageTuple> messages = new ArrayList<>();
    @EJB
    private SessionsDbLocal sessionsDbBean;

    @Lock(LockType.WRITE)
    @Override
    public void sendMessage(StreamMessage message) {
        messages.add(new MessageTuple(message));
        sessionsDbBean.sendToAll(new WebSocketPacket(
                WebSocketPacket.Type.STREAM_MESSAGES, Collections.singletonList(message), true));
    }

    @Lock(LockType.READ)
    @Override
    public List<StreamMessage> getMessages(Long time) {
        List<StreamMessage> list = new ArrayList<>();

        for (int i = messages.size() - 1; i >= 0; i--) {
            MessageTuple tuple = messages.get(i);
            if (tuple.getTime() < time) {
                break;
            }
            list.add(tuple.getMessage());
        }

        Collections.reverse(list);
        return list;
    }

    private class MessageTuple {
        private StreamMessage message;
        private long time;

        public MessageTuple() {
        }

        public MessageTuple(StreamMessage message) {
            this(message, new Date().getTime());
        }

        public MessageTuple(StreamMessage message, long time) {
            this.message = message;
            this.time = time;
        }

        public StreamMessage getMessage() {
            return message;
        }

        public void setMessage(StreamMessage message) {
            this.message = message;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }
}
