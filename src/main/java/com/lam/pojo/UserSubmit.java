package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
//用户与订单之间的联系表结构
@RestController
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSubmit {
private Integer id;
private Integer uid;
private Integer order_id;
}
