package com.lam.mapper;

import com.lam.pojo.Order;
import com.lam.pojo.OrderDetails;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderMapper {
    //    查询某张订单的详情数据 根据订单id（order_id）
    @Select("select * from order_details where order_id = #{order_id}")
    public List<OrderDetails> orderDetails(Integer order_id);

    //    插入数据
    @Insert("insert into order_details(order_id, pd_id, number, totals) VALUES (#{order_id},#{pd_id},#{number},#{totals})")
    public void insertDetail(OrderDetails orderDetails) throws Exception;


    //    @Insert("insert into orders(money, amount, time, state, exp_id) VALUES(#{money},#{amount},#{time},#{state},#{exp_id})")
//    public void insertOrder(Order order);
//    把所有的order信息写入order表
    @Update("update orders set money=#{money}, amount=#{amount}, state=#{state},exp_id=#{exp_id} ,uid=#{uid},add_id=#{add_id},order_number=#{order_number},address=#{address},phone=#{phone},contacts=#{contacts} where order_id = #{order_id}")
    public void insertOrder(Order order);

    //    更新订单状态
    public void updateOrder(Order order) throws Exception;

    //根据订单号来查询订单
    @Select("select order_id, money, amount, time, state, exp_id, order_number, uid, add_id, address, phone, contacts from orders where order_number = #{order_number}")
    public Order queryOrder(String order_number) throws Exception;

    //分页浏览订单
    @Select("select order_id, money, amount, time, state, exp_id, order_number, uid, add_id, address, phone, contacts from orders limit 15 offset #{start};")
    public List<Order> orderBrowse(Integer start) throws Exception;

    //根据订单ID搜出订单号order_number
    @Select("select order_number from orders where order_id = #{order_id}")
    public String searchId(Integer order_id);

    //每次返回10条指定状态的订单数据
    @Select("select order_id, money, amount, time, state, exp_id, order_number, uid, add_id, address, phone, contacts from orders where state=#{status} limit 10 offset #{offset}")
    public List<Order> orderList(String status, Integer offset);

    //查看不同状态的订单有多少条
    @Select("select count(*) from orders where state=#{status}")
    public int orderCount(String status);

    //根据订单去查订单的详细购买数据
    @Select("select * from order_details where order_id = #{order_id}")
    public List<OrderDetails> findOrderDetail(Integer order_id) throws Exception;

    //用户获取订单 暂时不分页
    @Select("select * from orders where uid = #{uid} and state = #{state} order by order_id desc")
    public List<Order> queryOrderState(Integer uid, String state);

    //用户确认订单
    @Update("update orders set state = 'finish' where order_id = #{order_id} and uid=#{uid}")
    public void userConfirmOrder(Integer order_id, Integer uid) throws Exception;

    //用户退回订单
    @Update("update orders set state = 'refund' where order_id = #{order_id} and uid=#{uid}")
    public void userRefundOrder(Integer order_id, Integer uid) throws Exception;
}
