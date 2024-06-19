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
public class Product {
    private Integer pd_id;//商品ID
    private String price;//商品价格
    private String state;//商品状态
    private String p_name;//商品名称
    private String p_describe;//商品描述
    private String picture_name;//商品首页展示的图片名
    private String pd_type;//产品的类型
    private LocalDateTime time;//修改or添加的时间
    private Integer number_single;//仅在浏览商品时使用 该变量表明在某张订单中用户某个商品的数量是多少
}
