package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Integer id; //购物车字段的id
    private Integer amount; //单个商品的数量
    private Integer pd_id;//购物车商品的id
    private Integer uid; //用户id （属于那个用户的
    private LocalDateTime join_time; //加入购物车的时间
}
