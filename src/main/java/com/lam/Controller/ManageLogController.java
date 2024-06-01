package com.lam.Controller;

import com.lam.Utils.CheckPower;
import com.lam.Utils.UserTheadLocal;
import com.lam.mapper.ManageLogMapper;
import com.lam.pojo.Managelog;
import com.lam.pojo.Result;
import com.lam.pojo.TokenUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//获取管理员操作订单日志
@RestController
public class ManageLogController {
    @Autowired
    private ManageLogMapper manageLogMapper;
    @GetMapping("/api/Mange/show")
    public Result getManageMsg(Integer start){
        if(start == null){
            return Result.error("缺少必要的参数");
        }
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
//        判断该账号是否归属管理员
        if (!CheckPower.check(tokenUserInfo.getAuthorization())) {
            return Result.error("该账号没有权限");
        }
        List<Managelog> msg = manageLogMapper.getMSG(start);
        if (msg.isEmpty()){
            return Result.success("没有日志");
        }else {
            return Result.success(msg);
        }


    }
}
