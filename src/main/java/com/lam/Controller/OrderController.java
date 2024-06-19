package com.lam.Controller;

import com.lam.Utils.CheckPower;
import com.lam.Utils.UserTheadLocal;
import com.lam.mapper.ManageLogMapper;
import com.lam.mapper.OrderMapper;
import com.lam.mapper.ProductMapper;
import com.lam.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
public class OrderController {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ManageLogMapper manageLogMapper; //操作日志
    @Autowired
    private ProductMapper productMapper;

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
            if (!(order.getState() == null && order.getExp_id() == null)) {
                manageLogMapper.insertMangeLog(tokenUserInfo.getId(), order_number, actions, t);
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

    //新增的接口文档中还不存在
    //根据订单状态查询订单数据
    @RequestMapping("/api/order")
    public Result orderList(String status, Integer offset) {
        // System.out.println(status+"and"+offset);
        if (status.equals("wait") || status.equals("sign") || status.equals("refund") || status.equals("finish")) {
            List<Order> orders = orderMapper.orderList(status, offset);
            return Result.success(orders);
        } else {
            return Result.error("关键字出错");
        }
    }

    //新增的接口文档中还不存在
    //查看不同状态的订单条数
    @RequestMapping("/api/order/count")
    public Result orderCount(String status) {
        if (status.equals("wait") || status.equals("sign") || status.equals("refund") || status.equals("finish")) {
            int count = orderMapper.orderCount(status);
            return Result.success(count);
        } else {
            return Result.error("关键字出错");
        }
    }

    //新增的接口文档中还不存在
    @RequestMapping("/api/order/detail")
    public Result orderDetail(Integer orderID) {
        try {
            List<OrderDetails> orderDetail = orderMapper.findOrderDetail(orderID);
            return Result.success(orderDetail);
        } catch (Exception e) {
            log.info("出错了，可能是订单不存在");
            return Result.error("出错了，可能是订单不存在");
        }
    }

    //    根据订单的id返回订单编号（接口文档中不存在该接口
    @RequestMapping("/api/order/info")
    public Result orderInfo(Integer id) {
        String s = orderMapper.searchId(id);
        return Result.success(s);
    }

    //用户浏览订单
    @RequestMapping("/api/order/user/browser")
    public Result userBrowserOrder(String state) {
//        UserBrowserOrder userBrowserOrder = new UserBrowserOrder();
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        List<Order> orders = orderMapper.queryOrderState(tokenUserInfo.getId(), state);
        //还需要包含一个首页的图
        for (int i = 0; i < orders.size(); i++){
            try {
                //获取该用户下所有的订单数据
                List<OrderDetails> orderDetail = orderMapper.findOrderDetail(orders.get(i).getOrder_id());
                //从订单数据中遍历，找到每个pd_id的产品信息
                List<Product> products = new ArrayList<>();
                orderDetail.forEach((k) -> {
                    try {
                        Product product = productMapper.queryProductInfo(k.getPd_id());
                        product.setNumber_single(k.getNumber());
                        products.add(product);
                    }catch (Exception e){
                        log.info("错误");
                    }
                });
                orders.get(i).setProducts(products);
            } catch (Exception e) {
                log.info("错误");
            }
        }
        return Result.success(orders);
    }

}
