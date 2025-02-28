package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//use lombok replace traditional get and set functions
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String phone;
    private String user_name;
    private String user_pwd;
    private String gender;
    private LocalDateTime register_time; //注册时间
    private int display; //是否显示
    private String roles; //角色
}
