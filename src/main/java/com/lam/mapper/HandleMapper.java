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

    @Select("select * from handle limit 10 offset #{start};")
    public List<Handle> allMessage(Integer start);
}
