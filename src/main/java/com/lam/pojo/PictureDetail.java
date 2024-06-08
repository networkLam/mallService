package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureDetail {
    private Integer pt_id; //详情图片数据库的主键
    private String pt_path; //图片路径
    private Integer pd_id; //绑定的商品id
}
