package com.lam.Utils;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;

//负责存储session
public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        //1.get http within session
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        //2.save session
        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }
}
