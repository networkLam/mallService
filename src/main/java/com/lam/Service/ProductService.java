package com.lam.Service;

import com.lam.mapper.ProductMapper;
import com.lam.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;
    public int determiner(Product product){
        if(product.getPrice() == null || product.getState() == null || product.getP_describe() == null || product.getPd_type() == null || product.getPicture_name() == null){
            System.out.println("文本不完整，结束。");
            return 0;
        }else{
            System.out.println("executed finished");
            return productMapper.addProduct(product);
        }

    }
}
