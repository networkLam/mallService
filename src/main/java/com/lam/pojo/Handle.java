package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
