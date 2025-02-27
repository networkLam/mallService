package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private boolean isSystem; //是否为系统消息
    private String message; //消息的类型是什么
    private String code ;//消息的状态是什么

}
