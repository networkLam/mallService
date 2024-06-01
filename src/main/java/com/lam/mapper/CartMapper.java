package com.lam.mapper;

import com.lam.pojo.Cart;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CartMapper {
    @Insert("insert into cart(amount, pd_id, uid,join_time) VALUES(#{amount},#{pd_id},#{uid},#{join_time})")
    public void addCart(Integer amount, Integer pd_id, Integer uid, LocalDateTime join_time) throws Exception;

    @Select("select id, amount, pd_id from cart where uid = #{uid}")
    public List<Cart> querySelfCart(Integer uid);

//    删除某样商品
    @Delete("delete from cart where id = #{id} and uid = #{uid}")
    public void deleteSelfCart(Integer id,Integer uid) throws Exception;
//    查询商品是否存在购物车中
    @Select("select pd_id,amount from cart where uid=#{uid} and pd_id = #{pd_id}")
    public Cart isExist(Integer pd_id,Integer uid) throws Exception;

//    如果购物车中存在商品则使商品数量加一
    @Update("update cart set amount = #{amount} where uid=#{uid} and pd_id = #{pd_id} ")
    public void increaseAmount(Integer amount,Integer uid,Integer pd_id);

    //修改购物车某样商品的数量
    @Update("update cart set amount = #{amount} where uid=#{uid} and pd_id = #{pd_id}")
    public void modifyAmount(Integer amount,Integer uid,Integer pd_id) throws Exception;
}
