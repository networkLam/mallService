package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/*
*  id int auto_increment not null ,
    m_id int not null ,
    pd_id int not null ,
    actions varchar(128) not null ,
    time datetime not null ,
*
* */
@RestController
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Handle {
    private Integer id;
    private Integer m_id;
    private Integer pd_id;
    private String actions;
    private LocalDateTime time;
}
