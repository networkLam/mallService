package com.lam.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionDTO {
    private Integer c_id; //收藏的ID
    private Integer pd_id; //商品的id
    private String price; //价格
    private String p_describe; //商品描述
    private String state; //商品状态
    private LocalDateTime date; //加入收藏的时间
    private String picture_name;//商品封面图
}
