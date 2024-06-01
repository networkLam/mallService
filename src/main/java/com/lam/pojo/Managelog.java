package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/*
* create table managelog
(
    id      int auto_increment not null,
    m_id    int                not null,
    order_number  varchar(13)        not null,
    actions varchar(128)       not null,
    time    datetime           not null,
    primary key (id),
    foreign key (m_id) references manager (m_id)
)
*
* */
@RestController
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Managelog {
    private Integer id;
    private Integer m_id;
    private String order_number;
    private String actions;
    private LocalDateTime time;
}
