package com.lam.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLogDTO {
    private Integer id;
    private Integer m_id;
    private String order_number;
    private String actions;
    private LocalDateTime time;
    private String userName; //用户名
}
