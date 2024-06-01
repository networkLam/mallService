package com.lam.mapper;

import com.lam.pojo.Manager;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ManageMapper {
//    管理员登录
    @Select("select  * from manager where phone = #{phone} and m_pwd = #{m_pwd}")
    public List<Manager> login(String phone,String m_pwd);
//注册管理员信息 （插入的时候报错该如何解决？）
    @Insert("insert into manager(phone, m_pwd, name, gender, entry_time) values (#{phone},#{m_pwd},#{name},#{gender},#{entry_time})")
    public void RegisterAdministrator(Manager manager) throws Exception;
//    查询该号码是否被使用过
    @Select("select * from manager where phone = #{phone}")
    public List<Manager> checkPhone(String phone) ;

    public int updateInfo(Manager manager);

//    查询该号码是否归属管理员
    @Select("select * from manager where phone = #{phone}")
    public Manager belongAdmin(String phone);




}
