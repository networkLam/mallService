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

    /*
    * 详情页面的图片逻辑
    * 当点击删除按钮的时候发送一个删除请求把选中的数据（id）发往服务器，再又服务器执行
    * 添加图片则是先把图片全部上传到服务器，再由服务器把所有的图片数据返回（而不是在本地比较返回）
    * */
    public Boolean detailProductInsert(String[] paths,Integer pd_id){
        for(String path:paths){
            try {
                //往数据库中插入图片
                productMapper.detailPicture(path,pd_id);
            }catch (Exception e){
                //异常就结束插入
                return false;
            }
        }
        //完成插入返回true
        return true;
    }
}
