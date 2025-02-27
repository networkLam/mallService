//package com.lam.websocket;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.IOException;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//@Component
//public class MyWebSocketHandler extends TextWebSocketHandler {
//    private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
//
////    private ConcurrentHashMap<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
////        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
////        tokenUserInfo.getId();
////        userSessions.put("user", session);
//        sessions.add(session);
//        System.out.println("New connection established: " + session.getId());
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        System.out.println("Received message: " + message.getPayload());
//        //消息从这里返回给服务器
//        session.sendMessage(new TextMessage("Hello, Client!"));
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        sessions.remove(session);
////        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
////        userSessions.remove(tokenUserInfo.getId().toString());
//        System.out.println("Connection closed: " + session.getId());
//    }
//
//    // Method to send a message to all connected clients
//    public void sendToAllClients(String message) {
////        System.out.println("I running ?");
////        Set<Map.Entry<String, WebSocketSession>> entries = userSessions.entrySet();
////        try {
////            for (Map.Entry<String, WebSocketSession> entry : entries) {
////                WebSocketSession session = entry.getValue();
////                session.sendMessage(new TextMessage(message));
////                System.out.println("I running ?");
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//
//        for (WebSocketSession session : sessions) {
//            try {
//                session.sendMessage(new TextMessage(message));
//                System.out.println("I running ?");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    // Method to send a message to a specific client
//    public void sendToClient(String sessionId, String message) {
////        WebSocketSession session = userSessions.get(sessionId);
////        try {
////            session.sendMessage(new TextMessage(message));
////        }catch(Exception e){
////            e.printStackTrace();
////        }
//
//        for (WebSocketSession session : sessions) {
//            if (session.getId().equals(sessionId)) {
//                try {
//                    session.sendMessage(new TextMessage(message));
//                    break;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//}
