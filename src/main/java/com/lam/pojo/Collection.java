package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
@RestController
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    private Integer id;
    private Integer uid;
    private Integer pd_id;
    private LocalDateTime date;
}
