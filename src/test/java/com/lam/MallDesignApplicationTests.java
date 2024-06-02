package com.lam;

import com.lam.Service.ManagerService;
import com.lam.Service.ProductService;
import com.lam.Service.UserService;
import com.lam.mapper.CollectionMapper;
import com.lam.mapper.ManageMapper;
import com.lam.mapper.ProductMapper;
import com.lam.mapper.UserMapper;
import com.lam.pojo.*;
import com.lam.pojo.Collection;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.DoubleStream;

@SpringBootTest
class MallDesignApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private ManageMapper manageMapper;
    @Autowired
    private UserSubmitMultiple.Product_Info product_info;
    @Autowired
    private UserSubmitMultiple userSubmitMultiple;
    @Autowired
    private Order order;
    @Autowired
    private CollectionMapper collectionMapper;
    //    private Product
    @Test
    public void testAllUserInfo() {
//        List<User> userList = userMapper.show();
//        userList.stream().forEach(user -> {
////            System.out.println(user.getUser_name());
////            System.out.println(user.getUser_pwd());
////            System.out.println(user.getGender());
////            System.out.println(user.getRegister_time());
////            System.out.println(user.getUID());
//            System.out.println(user);
//            System.out.println("--divide line--");
//        });
        List<User> show = userMapper.show();
        System.out.println(show);
    }

    //    产品更新接口通过
    @Test
    public void insertTest() {
        User user = new User();
        user.setUID(115);
        user.setUser_name("tom");
        user.setUser_pwd("119");
        user.setPhone("12345678910");
        user.setGender("男");
//        user.setRegister_time("2024-05-06");
//        userMapper.insertUser(user);
//        do you know why you get UID ? because your UID not is database generate .(is manual input data)
        // general the database primary key use system generate .
        System.out.println(user.getUID());
    }

    @Test
    public void UpdateTest() {
        Product product = new Product();
        product.setPd_id(73);
        product.setPicture_name("4.jpg");
//        product.setPrice("199999");
//        product.setState("1");
//        product.setP_describe("unknown scribe");
//        product.setPd_type("phone");
        int i = productMapper.updateInformation(product);
        System.out.println(i);
    }

    @Test
    public void addTest() {
        Product product = new Product();
        product.setPicture_name("6.jpg");
        product.setPrice("299999");
        product.setState("1");
        product.setP_describe("unknown object");
        product.setPd_type("ipad 2024");
//        int i = productMapper.addProduct(product);

        int determiner = productService.determiner(product);
        System.out.println(determiner);
    }

    @Test
    public void userRegisterTest() throws Exception {
//        User user = new User();
//        user.setUser_name("mayday");
//        user.setUser_pwd("cgg");
//        user.setPhone("12345678910");
//        user.setGender("男");
//        int i = userMapper.userRegister(user);
        int register = userService.register("1789", "reason");

        System.out.println(register);
    }

    @Test
    public void ManageRegister() {
        int register = managerService.Register("10086", "167");
        System.out.println(register);
    }

    @Test
    public void manageLogin() {
        try {
            List<Manager> login = manageMapper.login("10086", "167");
            System.out.println(login.isEmpty());
            System.out.println(login);
        }catch (Exception e){
            System.out.println(e.getMessage());

        }


    }

    @Test
    public void manageUpdate() {
        Manager manager = new Manager();
        manager.setGender("man");
        manager.setM_id(1);
        int row = manageMapper.updateInfo(manager);
        System.out.println(row);
//        System.out.println(login);
    }

    @Test
    public void productTest() throws Exception {
//测试时间戳 1716626260718 一共13位
        System.out.println(System.currentTimeMillis());
//        String price = productMapper.productQuery(55);
//        System.out.println(price);

    }

    @Test
    public void TestOder() throws Exception {
//        userSubmitMultiple.setId(4);
//        userService.submitOrder(userSubmitMultiple);
        userSubmitMultiple.setId(2);
        userSubmitMultiple.setAdd_id(4);
        List<UserSubmitMultiple.Product_Info> ls = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
//            不能重复使用同一个对象，因为指针的指向相同。
            UserSubmitMultiple.Product_Info p1 = new UserSubmitMultiple.Product_Info();
            p1.setAmount(i + 1);
            p1.setPd_id(58 + i);
            System.out.println("++++++++++++");
            System.out.println(p1);
            System.out.println("++++++++++++");
            ls.add(p1);
        }
        System.out.println(ls);
        userSubmitMultiple.setProductList(ls);
        userService.submitOrder(userSubmitMultiple);
        System.out.println("----------------");
        System.out.println(userSubmitMultiple);
        System.out.println("----------------");

//        LocalDateTime time = LocalDateTime.now();
//        order.setTime(time);
//        userMapper.submit(order);
    }

    //token Test
    @Test
    public void tokenTest() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", "lam");//这是我存进去的
        claims.put("pwd", "12138");
        System.out.println(claims);
//    DoubleStream jwts;
        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "lam13")
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 12 * 3600 * 1000))
                .compact();
        System.out.println(token);
    }
    @Test
    public void jwtDecode() {
        Claims lam13 = Jwts.parser()
                .setSigningKey("lam13")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJwd2QiOiIxMjEzOCIsImV4cCI6MTcxNjI1ODg0NiwidXNlcm5hbWUiOiJsYW0ifQ.v5sY7TZxcq3JLmSV75h7qihCHCxoSkhgGCO48KTw5Cs")
                .getBody();
        System.out.println(lam13);
        System.out.println(lam13.get("pwd",String.class));//通过claims.get方法取出来
        String username = lam13.get("username",String.class);
        System.out.println(username);
    }

    @Test
    public void isExistTest() throws Exception {

        Collection exist = collectionMapper.isExist(1, 60);
        System.out.println(exist);
    }
}
