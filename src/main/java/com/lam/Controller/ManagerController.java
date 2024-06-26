package com.lam.Controller;

import com.lam.Service.ManagerService;
import com.lam.Utils.JwtUtil;
import com.lam.Utils.UserTheadLocal;
import com.lam.mapper.ManageMapper;
import com.lam.pojo.Manager;
import com.lam.pojo.Result;
import com.lam.pojo.TokenUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private ManageMapper manageMapper;

    //    该接口ok
    @PostMapping("/api/administrator/register")
    public Result Register(@RequestBody Manager manager) {
        int register = managerService.Register(manager.getPhone(), manager.getM_pwd());
        if (register == 0) {
            return new Result("0", "fail", "注册失败");
        } else {
            return new Result("1", "success", "注册成功");
        }
    }

    // 该接口ok
    @PostMapping("/api/administrator/login")
    public Result login(@RequestBody Manager manager) {
        System.out.println(manager);
        try {
            List<Manager> login = manageMapper.login(manager.getPhone(), manager.getM_pwd());
            if (login.isEmpty()) {
//            account not exist
                return new Result("0", "failed", "账号或密码有误，请检查！");
            }
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("name", login.get(0).getName());
            claims.put("phone", login.get(0).getPhone());
            claims.put("id", login.get(0).getM_id());
            claims.put("authorization", "admin");//管理员的权限是标识是admin
            return new Result("1", "success", JwtUtil.jwtBuilder(claims));
        } catch (Exception e) {

            return Result.error("系统出错");
        }
    }

    //get manage information
    @RequestMapping("/api/administrator/info")
    public Result getInfo(){
        //获取线程数据
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        //获取线程中的id
        Integer id = tokenUserInfo.getId();
        Manager info = manageMapper.getInfo(id);
        return Result.success(info);
    }

//    获取管理员个人信息
    @RequestMapping("/api/administrator/action")
    public Result getAdminInfo(Integer id){

        Manager info = manageMapper.getInfo(id);
        return Result.success(info);
    }
}
