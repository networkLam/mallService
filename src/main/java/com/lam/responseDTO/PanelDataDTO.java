package com.lam.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PanelDataDTO {
    //今天的销售数据
    //昨天的销售数据
    //今天的订单
    //昨天的订单
    //今天的退款笔数
    //昨天的退款笔数
    //今天的新增用户
    //昨天的新增用户
    private Integer todaySale;
    private Integer yesterdaySale;
    private Integer todayOrder;
    private Integer yesterdayOrder;
    private Integer todayRefund;
    private Integer yesterdayRefund;
    private Integer todayNewUser;
    private Integer yesterdayUser;
    private Map<String,Object> last7SalesVolume;
    private Map<String,Object> productState; //商品的状态
}
