package com.lam.mapper;

import com.lam.pojo.Address;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressMapper {
//    查询用户上传的地址id是否正确
    @Select("select count(*) from address_info where addId=#{add_id} and uid = #{uid} ")
    public int queryExist(Integer add_id,Integer uid);
//查询某个地址ID返回的地址信息
    @Select("select * from address_info where addId = #{addId}")
   public Address queryAddId(Integer addId);

//  用户添加一个地址
    @Insert("insert into address_info(address, phone, contacts, uid) VALUES (#{address},#{phone},#{contacts},#{uid})")
    public void addAddress(Address address) throws Exception;

//    用户删除一个地址
    @Update("update address_info set display = 0 where addId=#{addId} and uid = #{uid}")
    public void deleteAddress(Integer addId,Integer uid) throws Exception;
    //用户浏览地址
    @Select("select * from address_info where uid=#{uid} and display = 1 order by addId desc")
    public List<Address> browser(Integer uid);
    //更新用户地址信息
    @Update("update address_info set address = #{address} ,phone = #{phone} , contacts = #{contacts} where addid = #{addId} and uid =#{uid}")
    public void updateAddress(Address address) throws Exception;
}
