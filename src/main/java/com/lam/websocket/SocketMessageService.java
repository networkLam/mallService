package com.lam.websocket;

import com.lam.pojo.Message;
import org.springframework.stereotype.Service;

@Service
public class SocketMessageService {
    private final ChatEndpoint chatEndpoint;

    public SocketMessageService(ChatEndpoint chatEndpoint){
        this.chatEndpoint = chatEndpoint;
    }

    public void sendToAllUser(String msg){
        chatEndpoint.broadcastAllUser(msg);
    }

}
