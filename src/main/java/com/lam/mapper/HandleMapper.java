package com.lam.mapper;

import com.lam.pojo.Handle;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface HandleMapper {
    @Insert("insert into handle(m_id, pd_id, actions, time) values(#{m_id},#{pd_id},#{actions},#{time})")
    public void insertInfo(Integer m_id, Integer pd_id, String actions, LocalDateTime time);

    //返回最新的10条操作记录
    @Select("select * from handle order by id desc limit 10 offset #{start} ")
    public List<Handle> allMessage(Integer start);
    //统计日志一共有多少条
    @Select("select count(*) from handle")
    public int count();
}
