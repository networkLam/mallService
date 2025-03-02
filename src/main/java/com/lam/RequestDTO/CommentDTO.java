package com.lam.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Integer orderId;//订单ID
    private Integer pdId; //产品ID
    private String date; // 评论时间
    private String comment; //评论内容
    private List<String> images; // 图片链接
    private Integer stars; // 星级评价
}
