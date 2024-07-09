package com.lam.Service;

import com.lam.mapper.OrderMapper;
import com.lam.pojo.CountAddress;
import com.lam.pojo.Order;
import com.lam.pojo.TotalSales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public int check(String destination, List<CountAddress> data) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getDestination().equals(destination)) { //相同就返回true
                return i;
            }
        }
        return -1;
    }

    public List<CountAddress> sort(List<CountAddress> data) {
        for (int i = 0; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                if (data.get(i).getTotal() < data.get(j).getTotal()) {
                    CountAddress temp_countAddress = data.get(j);
                    data.set(j, data.get(i));
                    data.set(i, temp_countAddress);
                }
            }
        }
        return data;
    }

    //分析最近100张订单的目的地(top 10)
    public List<CountAddress> analysisDestination() {
        List<String> address = orderMapper.queryLastOneHundredOrders();
        List<CountAddress> data = new ArrayList<CountAddress>();
        for (String s : address) {
            String[] add = s.split("/");
            int result = check(add[1], data);
            if (result >= 0) {
                data.get(result).setTotal(data.get(result).getTotal() + 1);
            } else {
                CountAddress countAddress = new CountAddress(add[1], 1);
                data.add(countAddress);
            }
        }
        if (data.size() > 10) {
            return sort(data).subList(0, 6);
        } else {
            return sort(data);
        }
    }

    public int check(LocalDate localDate, List<TotalSales> totalSales) {
        for (int i = 0; i < totalSales.size(); i++) {
            if (totalSales.get(i).getLocalDate().isEqual(localDate)) { //找到就返回索引位置
                return i;
            }
        }
        return -1;
    }

    public  List<TotalSales>  totalSales(){
        LocalDate localDate = LocalDate.now().minusDays(7);
        List<Order> order_details = orderMapper.totalSales(localDate);
        List<TotalSales> totalSales_data = new ArrayList<>();
        for (Order orderDetail : order_details) {
            LocalDateTime time = orderDetail.getTime();
            double money = Double.parseDouble(orderDetail.getMoney());
            int index = check(time.toLocalDate(), totalSales_data);
            if (index >= 0) {
                totalSales_data.get(index).setTotal(totalSales_data.get(index).getTotal() + money);
            } else {
                TotalSales temp_totalSales = new TotalSales(time.toLocalDate(), money);
                totalSales_data.add(temp_totalSales);
            }
        }
        return totalSales_data;
    }

}
