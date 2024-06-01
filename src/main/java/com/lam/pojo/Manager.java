package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

//管理人员对象
@RestController
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager {
private Integer m_id;
private String phone;
private String m_pwd;
private String name;
private String gender;
private LocalDateTime entry_time;
}
