package com.lam.Service;

import com.lam.Utils.UserTheadLocal;
import com.lam.mapper.AddressMapper;
import com.lam.mapper.OrderMapper;
import com.lam.mapper.ProductMapper;
import com.lam.mapper.UserMapper;
import com.lam.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private Order order;
    @Autowired
    private OrderDetails orderDetails; //订单详情对象
    @Autowired
    private AddressMapper addressMapper;

    public int register(String phone, String pwd) throws Exception {
        User user = new User();
        LocalDateTime dateTime = LocalDateTime.now();
        user.setPhone(phone);
        user.setUser_pwd(pwd);
        user.setUser_name("defaultUser");
        user.setGender("other");
        user.setRegister_time(dateTime);

        return userMapper.userRegister(user);
    }
//用户下单商品的业务逻辑
    public boolean submitOrder(UserSubmitMultiple userSubmitMultiple) throws Exception {
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        LocalDateTime t = LocalDateTime.now();//获取当前时间
        Random random = new Random();
        order.setState("待发货"); //设置订单状态
        order.setExp_id(String.valueOf( random.nextInt(10))+String.valueOf(System.currentTimeMillis()).substring(0,11));//设置快递编号
        order.setTime(t);//设置下单时间
        //订单id
//        order.setOrder_number(userSubmitMultiple.getId()+String.valueOf(System.currentTimeMillis()));//设置订单编号
        order.setOrder_number(tokenUserInfo.getId()+String.valueOf(System.currentTimeMillis()));//设置订单编号
        //正确的返回值应该是1
//        int count = addressMapper.queryExist(userSubmitMultiple.getAdd_id(),userSubmitMultiple.getId());
        int count = addressMapper.queryExist(userSubmitMultiple.getAdd_id(),tokenUserInfo.getId());
        System.out.println("count = "+count);
        if (count < 1 || userSubmitMultiple.getAdd_id() == null){
//            告诉用户没有地址 or this address not  belong to you .
            System.out.println("告诉用户没有拥有此地址 or this address not  belong to you .");

            return false;
        }
        order.setAdd_id(userSubmitMultiple.getAdd_id());//设置用户地址
        int addId =  userSubmitMultiple.getAdd_id();
        //查询该id返回查询到的地址信息
        Address address = addressMapper.queryAddId(addId);
        System.out.println(address);
        int total_g = 0; //订单商品的总个数
        double total_money = 0;//订单的总钱数
        //拿到用户ID
//        int userId = userSubmitMultiple.getId();
//        order.setUid(userId);

        order.setUid(tokenUserInfo.getId());
        System.out.println("用户id是:"+tokenUserInfo.getId());
        List<UserSubmitMultiple.Product_Info> productList = userSubmitMultiple.getProductList();
        userMapper.submit(order);//执行这个SQL语句后返回ID保存在order对象中
        int orderId = order.getOrder_id(); //获取订单返回的ID
        System.out.println("订单返回的ID是："+orderId);
//        userMapper.userAndOrderContact(userId, orderId); //此时用户id已于订单id建立了联系
        orderDetails.setOrder_id(orderId); //设置订单的id（把订单详情的ID与订单绑定一起）
        for (UserSubmitMultiple.Product_Info productInfo : productList) {
            //写入单个商品的信息
            int productId = productInfo.getPd_id();
            int amount = productInfo.getAmount();
            total_g+=amount;
            String price = productMapper.productQuery(productId);//查询商品价格
            double total_price = Double.parseDouble(price) * amount;//计算总价格
            total_money += total_price; //计算总钱数
//            String price_str = String.format("%.2f", total_price); //保留两位小数并转为字符串
            String price_str = String.valueOf(total_price);
            orderDetails.setPd_id(productId);//设置产品id
            orderDetails.setNumber(amount); //设置产品个数
            orderDetails.setTotals(price_str);//设置产品价格
            orderMapper.insertDetail(orderDetails);//写入订单详情
        }
        String  new_money = String.format("%.2f",total_money);
        order.setMoney(new_money);//设置价格
        order.setAmount(total_g);//设置总数量
        order.setPhone(address.getPhone());//设置收货号码
        order.setAddress(address.getAddress());//设置收货地址
        order.setContacts(address.getContacts());//设置收货联系人

        //写入订单
        System.out.println(order);
        orderMapper.insertOrder(order); //最后更新刚刚创建的row
        return true;
    }

}
