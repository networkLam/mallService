package com.lam.mapper;

import com.lam.pojo.Collection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CollectionMapper {
    //查询用户的购物车中有哪些商品
    @Select("select * from collection where uid = #{id}")
    public List<Collection> query(Integer id);

    //添加收藏
    @Insert("insert into collection(uid, pd_id, date) VALUES (#{uid},#{pd_id},#{date})")
    public void addCollection(Integer uid,Integer pd_id,  LocalDateTime date) throws Exception;
    //删除收藏
    @Delete("delete from collection where uid = #{uid} and pd_id = #{pd_id}")
    public void removeCollection(Integer uid,Integer pd_id) throws Exception;
    //查询商品是否已经收藏
    @Select("select * from collection where uid=#{uid} and pd_id=#{pd_id}")
    public Collection isExist(Integer uid,Integer pd_id) throws Exception;//判断是否已经在收藏列表
}
