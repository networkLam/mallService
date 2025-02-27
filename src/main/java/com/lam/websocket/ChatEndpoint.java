package com.lam.websocket;


import com.lam.Utils.GetHttpSessionConfig;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfig.class)
public class ChatEndpoint {

    private static final Map<String, Session> onlineUser = new ConcurrentHashMap<>();

    private HttpSession httpSession;

    /*
    建立websocket连接后调用
    * */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        //1.保存session
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        String user = (String) httpSession.getAttribute("user");
        onlineUser.put(user, session);
        //2.广播消息。（非必须）
//        broadcastAllUser("test!!!");
    }

    //广播方法

    public void broadcastAllUser(String message) {
        //遍历map集合
        try {
            for (Map.Entry<String, Session> stringSessionEntry : onlineUser.entrySet()) {
                Session session = stringSessionEntry.getValue();
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 浏览器发送消息时被调用
    * */
    @OnMessage
    public void onMessage(String message) {
//        JSON.parse()
        System.out.println(message);
    }

    //断开webSocket时触发
    @OnClose
    public void onClose(Session session) {
//        Session remove = onlineUser.remove(session);
        //1.移除session对象
        String user = (String) httpSession.getAttribute("user");
        onlineUser.remove(user);
        //2.notify

    }
}
