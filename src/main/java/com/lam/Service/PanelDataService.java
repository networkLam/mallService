package com.lam.Service;

import com.lam.mapper.PanelDataMapper;
import com.lam.pojo.Order;
import com.lam.pojo.Result;
import com.lam.responseDTO.PanelDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PanelDataService {
    @Autowired
    private PanelDataMapper panelDataMapper;

    public Result getData() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate yesterday = LocalDate.now().minusDays(1);
        //取出今天的销售数据
        Integer todaySaleData = panelDataMapper.getSaleData(today, tomorrow);
        //取出昨天的销售数据
        Integer YesterdaySaleData = panelDataMapper.getSaleData(yesterday, today);
        //取出今天的订单数和昨天的订单数
        Integer orderToday = panelDataMapper.getOrderData(today, tomorrow);
        Integer orderYesterday = panelDataMapper.getOrderData(yesterday, today);
        //取出昨天的和今天的退款笔数
        Integer todayRefundData = panelDataMapper.getRefundData(today, tomorrow);
        Integer yesterdayRefundData = panelDataMapper.getRefundData(yesterday, today);
        //取出今天的新增用户和昨天的新增用户
        Integer todayUserTotal = panelDataMapper.getUserTotal(today, tomorrow);
        Integer yesterdayUserTotal = panelDataMapper.getUserTotal(yesterday, today);
//        List<List<Order>> last7daySale = new ArrayList<>();
        Map<String, Object> last7 = new HashMap<>();
        List<LocalDate> dates = new ArrayList<>();
        List<Integer> sales7 = new ArrayList<>();
        //取出最近7天的销售数据
//        for (int i = 0; i < 7; i++) {
//            LocalDate preDate = LocalDate.now();
//            LocalDate lastDate = LocalDate.now().plusDays(1);
//            //最近7天的销售额包含退款的
//            Integer temp = panelDataMapper.getSalesData(preDate.minusDays(i), lastDate.minusDays(i));
//            sales7.add(temp);
//            dates.add(preDate.minusDays(i));
////            last7daySale.add(salesData);
//        }
        for (int i = 6; i >= 0; i--) { // 调整循环范围为6到0
            LocalDate preDate = LocalDate.now().minusDays(i);
            LocalDate lastDate = preDate.plusDays(1);

            // 最近7天的销售额包含退款的
            Integer temp = panelDataMapper.getSalesData(preDate, lastDate);
            sales7.add(temp);
            dates.add(preDate);
        }
        last7.put("dates", dates);
        last7.put("volume", sales7);
//        log.info("last 7 day data is = {}",last7daySale);
        //统计各个商品的状态
        Map<String, Object> state = new HashMap<>();
        Integer OnSales = panelDataMapper.getDifferentSateForProduct("上架");
        state.put("onSales", OnSales);
        Integer NotSales = panelDataMapper.getDifferentSateForProduct("下架");
        state.put("NotSales", NotSales);
        log.info("统计各个商品的状态 = {}", state);
        PanelDataDTO panelDataDTO = new PanelDataDTO();
        panelDataDTO.setTodaySale(todaySaleData);
        panelDataDTO.setYesterdaySale(YesterdaySaleData);
        panelDataDTO.setTodayOrder(orderToday);
        panelDataDTO.setYesterdayOrder(orderYesterday);
        panelDataDTO.setTodayRefund(todayRefundData);
        panelDataDTO.setYesterdayRefund(yesterdayRefundData);
        panelDataDTO.setTodayNewUser(todayUserTotal);
        panelDataDTO.setYesterdayUser(yesterdayUserTotal);
        panelDataDTO.setLast7SalesVolume(last7);
        panelDataDTO.setProductState(state);
        return Result.success(panelDataDTO);

    }
}
