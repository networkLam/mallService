package com.lam.responseDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
//响应操作日志的类
public class HandleLogDTO {
    private Integer id;
    private Integer m_id;
    private Integer pd_id;
    private String actions;
    private LocalDateTime time;
//    @Column(name = "p_name")
    private String ProductName; //商品名称 该字段信息来自 product表中的p_name
    private String userName; //用户名称；
}
