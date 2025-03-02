package com.lam.mapper;

import com.lam.pojo.ProductComment;
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
}
