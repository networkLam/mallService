package com.lam.mapper;

import com.lam.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PanelDataMapper {
    //    09 08
    @Select("select COALESCE(SUM(money), 0)  from orders where time >= #{preDate} and time <= #{lastDay} and state !='refund'")
    public Integer getSaleData(LocalDate preDate, LocalDate lastDay);

    //    @Select("select sum(money) from orders where time <= #{now} and time >= #{yesterday};")
//    public Integer getYesterdaySaleData(LocalDate now,LocalDate yesterday);
    //获取某天的订单数
    @Select("select count(*) from orders where time >= #{preDate} and time <= #{lastDay} and state !='refund'")
    public Integer getOrderData(LocalDate preDate, LocalDate lastDay);

    //获取某天的退款笔数
    @Select("select count(*) from orders where state = 'refund' and time >=#{preData} and time <= #{lastDay}")
    public Integer getRefundData(LocalDate preData, LocalDate lastDay);

    @Select("select count(*) from user where register_time >= #{preDate} and register_time <= #{lastDay}")
    public Integer getUserTotal(LocalDate preDate, LocalDate lastDay);

    //找出最近7天的销售额
    @Select("select COALESCE(SUM(money), 0) from orders where time >= #{preDate} and time <= #{lastDate}")
    public Integer getSalesData(LocalDate preDate, LocalDate lastDate);

    //统计各个状态的商品数量
    @Select("select count(*) from product where state = #{state}")
    public Integer getDifferentSateForProduct(String state);
}
