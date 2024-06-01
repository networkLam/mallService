package com.lam.mapper;

import com.lam.pojo.Order;
import com.lam.pojo.User;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserMapper {
    //    @Select("select * from user")
//    public List<User> show();
    public List<User> show();

    @Select("select phone,user_name,gender from user where uid = #{id}")
    public User returnUserInfo(Integer id);
    @Select("select count(*) from user")
    public int amount();

    //    删除某个用户 删除是有返回值 返回的值是int 表示影响了几条记录
    @Delete("delete  from user where uid=#{id}")
    public int deleteUser(String id);

    @Insert("insert into user(phone,user_name,user_pwd,gender,register_time) values (#{phone},#{user_name},#{user_pwd},#{gender},#{register_time})")
    public int userRegister(User user);

    //    查询用户身份是否存在
    @Select("select UID,phone,user_name,user_pwd from user where phone = #{phone} and user_pwd = #{user_pwd} ")
    public List<User> login(String phone, String user_pwd);

//    用于用户提交订单
    @Insert("insert into orders(time) values (#{time})")
    @Options(keyProperty = "order_id",useGeneratedKeys = true)
    public void submit(Order order);
//把用户ID与订单id联系一起
    @Insert("insert into submit(uid,order_id) values (#{uid},#{order_id})")
    public void userAndOrderContact(Integer uid,Integer order_id);
}
