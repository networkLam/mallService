package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductComment {
    private Integer id; //主键
    private Integer pd_id; //产品的ID
    private Integer user_id; //用户的id
    private LocalDateTime time; //评论时间
    private String comment; //评论内容
    private Integer stars; //星数
}
