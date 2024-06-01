package com.lam.mapper;

import com.lam.pojo.Address;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    @Delete("delete from address_info  where addId=#{id}")
    public void deleteAddress(Integer id)throws Exception;
}
