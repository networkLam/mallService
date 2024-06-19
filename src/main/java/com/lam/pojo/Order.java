package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer order_id; //订单id
    private String money; //总钱数
    private Integer amount; //数量
    private LocalDateTime time; //下单时间
    private String state; //状态
    private String exp_id; //这是快递编号
    private Integer uid; //用户id
    private Integer add_id;//地址ID
    private String address;//收货地址
    private String phone; //手机号码
    private String contacts;//联系人
    private String order_number;//订单编号
    private List<Product> products; //商品信息，仅在用户浏览订单时使用
}
