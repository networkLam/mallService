package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalSales {
    private LocalDate localDate; //销售日期
    private double total; //总额
}
