package com.lam.Controller;

import com.lam.Utils.CheckPower;
import com.lam.Utils.UserTheadLocal;
import com.lam.mapper.HandleMapper;
import com.lam.pojo.Handle;
import com.lam.pojo.Result;
import com.lam.pojo.TokenUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HandleController {
    @Autowired
    private HandleMapper handleMapper;
//    获取管理操作商品的日志
    @GetMapping("/api/handle/msg")
    public Result getAllMessage(Integer start) {
        if(start == null){
            return Result.error("缺少必要的参数");
        }
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
//        判断该账号是否归属管理员
        if (!CheckPower.check(tokenUserInfo.getAuthorization())) {
            return Result.error("该账号没有权限");
        }
        List<Handle> handles = handleMapper.allMessage(start);
        if (handles.isEmpty()){
            return Result.success("没有日志");
        }else {
            return Result.success(handles);
        }

    }

}
