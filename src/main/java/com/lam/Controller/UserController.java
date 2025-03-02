package com.lam.Controller;

import com.alibaba.fastjson.JSON;
import com.lam.Service.UserService;
import com.lam.Utils.CheckPower;
import com.lam.Utils.JwtUtil;
import com.lam.Utils.UserTheadLocal;
import com.lam.mapper.UserMapper;
import com.lam.pojo.*;
import com.lam.responseDTO.UserLoginDTO;
import com.lam.websocket.SocketMessageService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Autowired
    private SocketMessageService socketMessageService;
    //   管理员和用户登录接口
    @RequestMapping("/api/login")
    public Result userLogin(@RequestBody User user) {
        System.out.println("phone:" + user.getPhone() + "pwd:" + user.getUser_pwd());
       User systemUser = userMapper.login(user.getPhone(), user.getUser_pwd());
        System.out.println(systemUser);
        if (Objects.isNull(systemUser)) {
            return new Result("0", "登录失败,账号或密码有误，请检查。", "账号或密码有误，请检查。");
        }
//        String userName = login.get(0).getUser_name();
//        String userPhone = login.get(0).getPhone();
//        Integer Uid = login.get(0).getId();//获取用户ID
        HashMap<String, Object> claims = new HashMap<>();
        //token中不要放敏感信息
//        claims.put("name", userName);//用户名
//        claims.put("phone", userPhone);//手机号
        claims.put("id", systemUser.getId()); //用户ID
        claims.put("expired",new Date().getTime()+JwtUtil.TIME); //过期时间
//        System.out.println("用户id是：" + login.get(0).getId());
        String token = JwtUtil.jwtBuilder(claims);//下发token
//        new UserLoginDTO(user,token);
        return new Result("1", "登录成功",  new UserLoginDTO(systemUser,token));
    }

    //    该接口可用
    //    用户注册
    @PostMapping("/api/user/register")
    public Result userRegister(@RequestBody UserRegister userRegister) {
        if (userRegister.getPhone().isEmpty() || userRegister.getPwd().isEmpty()) {
            return new Result("0", "fail", "注册失败，请检查账号或密码！");
        } else {
            try {
                userService.register(userRegister.getPhone(), userRegister.getPwd());
                return new Result("1", "success", "注册成功");
            }catch (Exception e) {
                return Result.error("注册失败，系统中已存在此账号。");
            }
        }
    }
//    购买商品
    @PostMapping("/api/user/buy")
    public Result orderProcess(@RequestBody UserSubmitMultiple userSubmitMultiple) {
        System.out.println(userSubmitMultiple);
        try {
            Result result = userService.submitOrder(userSubmitMultiple);
            if(!result.getMsg().equals("success")){
                return result;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            System.out.println(e.fillInStackTrace());
            return new Result("404", "fail", "下单失败，无法购买。");
        }
        String str = JSON.toJSONString(new Message(true, "update", "200"));
        socketMessageService.sendToAllUser(str);//发送消息给所有的在线管理员
        return new Result("1", "success", "已成功下单。");
    }

    @RequestMapping("/api/userinfo")
    public Result getUserInfo(){
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        Integer id = tokenUserInfo.getId();
        return Result.success(userMapper.returnUserInfo(id));
    }
//新增的
//    返回用户总量
    @RequestMapping("/api/user/total")
    public Result userTotal(){
        int total = userMapper.userTotal();
        return Result.success(total);
    }
    //新增的
    //分页返回用户的信息
    @RequestMapping("/api/user/info")
    public Result userDivideBrowser(Integer start){
        List<User> users = userMapper.divideBrowser(start);
        return Result.success(users);
    }

    //更新用户名
    @PostMapping("/api/user/update/name")
    public Result updateUserName(@RequestBody User user){
        try{
            userMapper.updateUserName(user.getId(),user.getUser_name());
            System.out.println(user);
            return Result.success("更新成功");
        }catch (Exception e){
            return Result.error("更新失败");
        }
    }

    @RequestMapping("/api/user/rest/pwd")
    public Result restPWD(Integer uid){
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        if (!CheckPower.check(tokenUserInfo.getAuthorization())) {//判断当前访问的是否为管理员
            return Result.error("无权限访问");
        }
         userMapper.restUserPWD(uid);
        return Result.success("重置密码成功") ;
    }
    //隐藏用户
    @RequestMapping("/api/user/hide")
    public Result hideUser(Integer uid){
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        if (!CheckPower.check(tokenUserInfo.getAuthorization())) {//判断当前访问的是否为管理员
            return Result.error("无权限访问");
        }
        userMapper.deleteUser(uid);
        return Result.success("用户删除成功");
    }
    @GetMapping("/api/getip")
    public Result returnIP(HttpServletRequest request){
        System.out.println(request.getRemoteAddr());
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        // 如果有多个代理，取第一个非unknown的IP地址
        if (ipAddress != null && ipAddress.contains(",")) {
            String[] addresses = ipAddress.split(",");
            for (String ip : addresses) {
                if (!"unknown".equalsIgnoreCase(ip.trim())) {
                    ipAddress = ip.trim();
                    break;
                }
            }
        }
        return Result.success(ipAddress);
    }
}
