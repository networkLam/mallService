package com.lam.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

//添加商品的DTO
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductDTO {
    private Integer pd_id;
    private String p_name;//产品名称
    private String number;//商品数量
    private String p_describe;
    private String pd_type;
    private String picture_name;
    private String state;
    private LocalDateTime time;
    private String price;//价格
    private List<String> picture_detail;
}
