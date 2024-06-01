package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Integer addId;//地址编号
    private String address; //地址
    private String phone;//手机
    private String contacts;//联系人
    private Integer uid;//所属用户id
}
