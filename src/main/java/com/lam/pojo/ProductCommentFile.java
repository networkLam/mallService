package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCommentFile {
    private Integer id; //主键
    private Integer pc_id; //评论的主键ID
    private Integer file_name; //评论的图片名称
}
