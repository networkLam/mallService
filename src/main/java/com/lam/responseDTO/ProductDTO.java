package com.lam.responseDTO;

import com.lam.pojo.PictureDetail;
import com.lam.pojo.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Product product;
    private List<PictureDetail> pictureList;
    private Integer count; //商品评论条数
    private List<CommentViewDTO> commentInfo;//评论的内容
}
