package com.lam.mapper;

import com.lam.pojo.Handle;
import com.lam.responseDTO.HandleLogDTO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface HandleMapper {
    @Insert("insert into handle(m_id, pd_id, actions, time) values(#{m_id},#{pd_id},#{actions},#{time})")
    public void insertInfo(Integer m_id, Integer pd_id, String actions, LocalDateTime time);

    //返回最新的10条操作记录
    @Select("select handle.*, product.p_name,manager.name  from handle join product on handle.pd_id = product.pd_id join manager on  handle.m_id = manager.m_id order by handle.id  desc limit 10 offset #{start} ")
    @Results({
            @Result(property = "productName", column = "p_name"),
            @Result(property = "userName", column = "name")
    }
    )
    public List<HandleLogDTO> allMessage(Integer start);

    //统计日志一共有多少条
    @Select("select count(*) from handle")
    public int count();
}
