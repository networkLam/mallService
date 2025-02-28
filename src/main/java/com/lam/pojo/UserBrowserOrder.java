package com.lam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
* 订单id
*
*
*
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBrowserOrder {
    private List<Order> orders;
    private List<Product> products;
}
