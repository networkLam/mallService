package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
* 订单id
*
*
*
* */
@RestController
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBrowserOrder {
    private List<Order> orders;
    private List<Product> products;
}
