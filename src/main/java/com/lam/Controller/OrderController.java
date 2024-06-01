package com.lam.Controller;

import com.lam.Utils.CheckPower;
import com.lam.Utils.UserTheadLocal;
import com.lam.mapper.ManageLogMapper;
import com.lam.mapper.OrderMapper;
import com.lam.pojo.Order;
import com.lam.pojo.Result;
import com.lam.pojo.TokenUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
public class OrderController {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ManageLogMapper manageLogMapper; //操作日志

    //查询订单(管理员)
    @GetMapping("/api/order/query")
    public Result queryOrder(String number) {
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        if (!CheckPower.check(tokenUserInfo.getAuthorization())) {
            return Result.error("无权限访问");
        }
        try {
            Order order = orderMapper.queryOrder(number);
//            结果不为空返回结果，否则返回"无结果"
            return Result.success(Objects.requireNonNullElse(order, "无结果"));

        } catch (Exception e) {
            return Result.error("无查询结果");
        }

    }

    //    更新订单信息接口(仅能更新状态和快递编号，是订单的ID ，不是订单号order_number
    @PostMapping("/api/order/update")
    public Result updateOrder(@RequestBody Order order) {
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        LocalDateTime t = LocalDateTime.now();
        if (!CheckPower.check(tokenUserInfo.getAuthorization())) {
            return Result.error("无权限访问");
        }
        try {
            String actions = "";
            String order_number = orderMapper.searchId(order.getOrder_id());
            System.out.println(order_number);
            orderMapper.updateOrder(order);
            if (order.getExp_id() != null) {
                actions += "更改快递编号信息;";
            }
            if (order.getState() != null) {
                actions += "更改订单状态;";
            }
//            写入操作日志
            System.out.println(actions);
            if(!(order.getState() == null && order.getExp_id() == null)){
                manageLogMapper.insertMangeLog(tokenUserInfo.getId(),order_number,actions,t);
            }



        } catch (Exception e) {
            return Result.error("更新失败");
        }
        return Result.success("更新完成");

    }

    //分页浏览
    @GetMapping("/api/order/list")
    public Result orderPage(Integer start) {
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        if (!CheckPower.check(tokenUserInfo.getAuthorization())) {
            return Result.error("无权限访问");
        }
        try {
            List<Order> orders = orderMapper.orderBrowse(start);
            System.out.println(orders);
            return Result.success(orders);
        } catch (Exception e) {
            return Result.error("分页失败");
        }

    }

}
