package com.lam.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaitReviewsDTO {
    private Integer order_id; //订单的id
    private LocalDateTime time; //订单的时间
    private Integer id; //订单详情中的ID
    private String comment; //是否已经评价
    private String p_name; //商品名
    private String p_describe; //描述
    private String picture_name; //商品图片
    private Integer pd_id; //商品ID
    private Integer number; //购买的数量
    private String totals; //总价
    private Integer unit_price; //单价
}
