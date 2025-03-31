package com.lam.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//客户端购物车数据对象
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartViewDTO {
    private Integer id;
    private Integer amount;
    private LocalDateTime join_time;
    private Integer pd_id;
    private String p_name;
    private String p_describe;
    private String price;
    private  String pd_type;
    private String picture_name;
}
