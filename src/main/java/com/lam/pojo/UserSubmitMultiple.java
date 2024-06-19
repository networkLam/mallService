package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSubmitMultiple {
    private Integer id;//用户id
    private List<Product_Info> productList;//购买商品的列表
    private Integer add_id;//下单的地址
    private Integer gate;  //表示从那个界面进入的 有购物车和直接购买
    @RestController
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product_Info {
        public Integer pd_id;//商品id
        public int amount; //商品个数
    }


}
