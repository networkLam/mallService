package com.lam.mapper;

import com.lam.pojo.PictureDetail;
import com.lam.pojo.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    //    返回所有的产品（该方法仅供测试使用）
    @Select("select * from product")
    public List<Product> allProduct();

    //    计算一共有多少个商品
    @Select("select count(*) from product where display = 1")
    public int count();
//分页
    @Select("select * from product where display = 1 order by pd_id desc limit 5 offset #{start}  ")
    public List<Product> dividePage(Integer start);

//    allows information of some update
    public int updateInformation(Product product);

    //add product ,also allows  information of part add
    public int addProduct(Product product);

//    return the price of a certain product
    @Select("select price from product where pd_id = #{pd_id}")
    public String productQuery(Integer pd_id) throws Exception;

//    详情图片插入
    @Insert("insert into picture(pt_path, pd_id) VALUES (#{pt_path},#{pd_id})")
    public void detailPicture(String pt_path,Integer pd_id) throws Exception;
//请求详情页面的图片

    @Select("select * from picture where pd_id = #{pd_id}")
    public List<PictureDetail> queryPicture(Integer pd_id) throws Exception;
    //删除详情页的图片信息
    @Delete("delete from picture where pt_id = #{pt_id}")
    public void deletePictureInfo(Integer pt_id) throws Exception;
    //查询商品的信息 根据商品id
    @Select("select * from product where pd_id = #{pdId}")
    public Product queryProductInfo(Integer pdId) throws Exception;

    //暂不分页 搜索
    @Select("select * from product where p_describe like concat('%',#{keyword},'%') limit 10 offset 0 ")
    public List<Product> searchProduct(String keyword);

    @Update("update product set display = 0 where pd_id = #{pd_id}")
    public void deleteProduct(Integer pd_id);

}
