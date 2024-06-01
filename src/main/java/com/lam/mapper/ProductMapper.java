package com.lam.mapper;

import com.lam.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Mapper
public interface ProductMapper {

    //    返回所有的产品（该方法仅供测试使用）
    @Select("select * from product")
    public List<Product> allProduct();

    //    计算一共有多少个商品
    @Select("select count(*) from product")
    public int count();
//分页
    @Select("select * from product limit 5 offset #{start};")
    public List<Product> dividePage(Integer start);

//    allows information of some update
    public int updateInformation(Product product);

    //add product ,also allows  information of part add
    public int addProduct(Product product);

//    return the price of a certain product
    @Select("select price from product where pd_id = #{pd_id}")
    public String productQuery(Integer pd_id) throws Exception;



}
