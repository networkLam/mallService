package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
    private Integer id;
    private Integer order_id;
    private Integer pd_id;
    private Integer number;
    private String totals;
}
