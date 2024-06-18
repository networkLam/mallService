package com.lam.mapper;

import com.lam.pojo.Managelog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ManageLogMapper {
    @Insert("insert into managelog(m_id, order_number, actions, time) VALUES(#{m_id},#{order_number},#{actions},#{time}) ")
    public void insertMangeLog(Integer m_id, String order_number, String actions, LocalDateTime time);
//    @Insert("insert into managelog(order_number) VALUES(#{order_number}) ")
//    public void insertMangeLog(String order_number);
//    @Insert("insert into handle(m_id, pd_id, actions, time) values(#{m_id},#{pd_id},#{actions},#{time})")
//    public void insertInfo(Integer m_id, Integer pd_id, String actions, LocalDateTime time);
    @Select("select id, m_id, order_number, actions, time from managelog  order by id desc limit 10 offset #{start};")
    public List<Managelog> getMSG(Integer start);
    //获取订单日志的总行数
    @Select("select count(*) from managelog")
    public int count();
}
