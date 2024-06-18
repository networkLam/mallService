package com.lam.Controller;

import com.lam.Service.UserService;
import com.lam.Utils.CheckPower;
import com.lam.Utils.JwtUtil;
import com.lam.Utils.UserTheadLocal;
import com.lam.mapper.UserMapper;
import com.lam.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    //    用户登录接口
    @RequestMapping("/api/login")
    public Result userLogin(@RequestBody User user) {
        System.out.println("phone:" + user.getPhone() + "pwd:" + user.getUser_pwd());
        List<User> login = userMapper.login(user.getPhone(), user.getUser_pwd());
        System.out.println(login);
        if (login.isEmpty()) {
            return new Result("0", "登录失败", "账号或密码有误，请检查。");
        }
        String userName = login.get(0).getUser_name();
        String userPhone = login.get(0).getPhone();
        Integer Uid = login.get(0).getUID();
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("name", userName);//用户名
        claims.put("phone", userPhone);//手机号
        claims.put("id", Uid);
        claims.put("authorization","user");//表面用户
        System.out.println("用户id是：" + login.get(0).getUID());
        String token = JwtUtil.jwtBuilder(claims);//下发token
        return new Result("1", "登录成功", token);
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
                return new Result("0", "success", "注册成功");
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
            boolean t = userService.submitOrder(userSubmitMultiple);
            if (!t) {
                return new Result("0", "fail", "数据引用有误，下单失败，无法购买。");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            System.out.println(e.fillInStackTrace());
            return new Result("404", "fail", "下单失败，无法购买。");
        }
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
            userMapper.updateUserName(user.getUID(),user.getUser_name());
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
        return Result.success("重置密码成功");
    }
    @RequestMapping("/api/user/hide")
    public Result hideUser(Integer uid){
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        if (!CheckPower.check(tokenUserInfo.getAuthorization())) {//判断当前访问的是否为管理员
            return Result.error("无权限访问");
        }
        userMapper.deleteUser(uid);
        return Result.success("用户删除成功");
    }

}
