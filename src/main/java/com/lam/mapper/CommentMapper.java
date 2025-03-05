package com.lam.mapper;

import com.lam.pojo.ProductComment;
import com.lam.pojo.ProductCommentFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into product_comment(pd_id, user_id, time, comment) VALUES (#{pd_id},#{user_id},#{time},#{comment})")
    @Options(keyProperty = "id",useGeneratedKeys = true)
    public void addComment(ProductComment productComment);

//    @Insert("INSERT INTO pc_file(pc_id, file_name) VALUES (#{pc_id},#{file_name})")
    public void addFileOfComment(@Param("pc_id") Integer pcId,@Param("fileNames") List<String> fileNames);

    //modify order details status
    @Update("UPDATE order_details SET comment = '1',stars = #{stars} where order_id = #{order_id} and pd_id = #{pd_id}")
    public void modifyOrderStatus(Integer stars, Integer order_id,Integer pd_id);

    @Select("SELECT comment FROM order_details WHERE order_id = #{order_id} and pd_id = #{pd_id}")
    public String determineHasBeen(Integer order_id,Integer pd_id);

    //read product-reviews according to pdId,5 items each time
    @Select("select * from product_comment where pd_id = #{pdId} order by time desc  limit #{limit} offset #{offset}")
    public List<ProductComment> readProductReviews(Integer pdId,Integer limit,Integer offset); //offset 起始页
    //用评论的ID去找图
    @Select("select * from pc_file where pc_id = #{pvId}")
    public List<ProductCommentFile> readProductReviewsFile(Integer pvId);

    //altogether specific product how much the reviews
    @Select("select count(*) from product_comment where pd_id = #{pdId}")
    public int countReviews(Integer pdId);

}
