package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
//上传多张图片的对象
@RestController
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pictures {
    private Integer pd_id; //绑定的产品id
    private String[] pictures; //
}
