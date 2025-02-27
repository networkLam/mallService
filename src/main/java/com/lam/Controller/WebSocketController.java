package com.lam.Controller;


import com.alibaba.fastjson.JSON;
import com.lam.pojo.Message;
import com.lam.websocket.SocketMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {
    @Autowired
    private SocketMessageService socketMessageService;

    @GetMapping("/api/sendmessage")
    public String sendMessage(String text) {
        String str = JSON.toJSONString(new Message(true, "update", "200"));
        socketMessageService.sendToAllUser(str);
        System.out.println("要发送的消息是：" + text);
        return "Message sent to all clients!";
    }
}
