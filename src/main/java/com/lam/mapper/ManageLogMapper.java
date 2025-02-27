package com.lam.mapper;

import com.lam.pojo.Managelog;
import com.lam.responseDTO.OrderLogDTO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ManageLogMapper {
    @Insert("insert into order_log(m_id, order_number, actions, time) VALUES(#{m_id},#{order_number},#{actions},#{time}) ")
    public void insertMangeLog(Integer m_id, String order_number, String actions, LocalDateTime time);
//    @Insert("insert into managelog(order_number) VALUES(#{order_number}) ")
//    public void insertMangeLog(String order_number);
//    @Insert("insert into handle(m_id, pd_id, actions, time) values(#{m_id},#{pd_id},#{actions},#{time})")
//    public void insertInfo(Integer m_id, Integer pd_id, String actions, LocalDateTime time);
    @Select("select id,order_log.m_id, order_number, actions, time,manager.name from order_log join manager on order_log.m_id = manager.m_id order by id desc limit 10 offset #{start};")
    @Results({
            @Result(column = "name",property = "userName")
    })
    public List<OrderLogDTO> getMSG(Integer start);
    //获取订单日志的总行数
    @Select("select count(*) from order_log")
    public int count();
}
