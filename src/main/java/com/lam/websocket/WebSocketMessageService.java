//package com.lam.websocket;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class WebSocketMessageService {
//
//    private final MyWebSocketHandler myWebSocketHandler;
//
//    // Inject the WebSocket handler into your service
//    public WebSocketMessageService(MyWebSocketHandler myWebSocketHandler) {
//        this.myWebSocketHandler = myWebSocketHandler;
//    }
//
//    public void sendMessageToAll(String message) {
//        myWebSocketHandler.sendToAllClients(message);
//    }
//
//    public void sendMessageToClient(String sessionId, String message) {
//        myWebSocketHandler.sendToClient(sessionId, message);
//    }
//}
