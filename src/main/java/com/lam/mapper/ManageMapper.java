package com.lam.mapper;

import com.lam.pojo.Manager;
import com.lam.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ManageMapper {
    //    管理员登录
    @Select("select  * from manager where phone = #{phone} and m_pwd = #{m_pwd}")
    public List<Manager> login(String phone, String m_pwd) throws Exception;

    //注册管理员信息 （插入的时候报错该如何解决？）
    @Insert("insert into manager(phone, m_pwd, name, gender, entry_time) values (#{phone},#{m_pwd},#{name},#{gender},#{entry_time})")
    public void RegisterAdministrator(Manager manager) throws Exception;

    //    查询该号码是否被使用过
    @Select("select * from manager where phone = #{phone}")
    public List<Manager> checkPhone(String phone);

    public int updateInfo(Manager manager);

    //    查询该号码是否归属管理员
    @Select("select * from manager where phone = #{phone}")
    public Manager belongAdmin(String phone);


    //    获取管理员的个人信息
    @Select("select * from manager where m_id=#{id}")
    public Manager getInfo(Integer id);

    //查找用户信息
    @Select("select id,user_name, phone, user_pwd, gender, register_time, roles from user where id = #{id}")
    public User findUserId(String id);

    //用户修改名称
    @Update("update user set user_name = #{name} where phone = #{phone}")
    public void updateName(String name,String phone);

    @Update("update user set gender = #{gender} where phone = #{phone}")
    public void updateGender(String gender,String phone);
}
