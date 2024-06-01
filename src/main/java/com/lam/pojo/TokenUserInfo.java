package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenUserInfo {
    private Integer id; //id
    private String name; //用户名
    private String phone; //手机号
    private String pwd; //密码
    private String authorization;//批准（权限

}
