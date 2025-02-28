package com.lam.Controller;

import com.lam.mapper.ManageLogMapper;
import com.lam.pojo.Result;
import com.lam.responseDTO.OrderLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//获取管理员操作订单日志
@RestController
public class ManageLogController {
    @Autowired
    private ManageLogMapper manageLogMapper;
    @GetMapping("/api/admin/log/product")
    public Result getManageMsg(Integer start){
        if(start == null){
            return Result.error("缺少必要的参数");
        }
//        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
////        判断该账号是否归属管理员
//        if (!CheckPower.check(tokenUserInfo.getAuthorization())) {
//            return Result.error("该账号没有权限");
//        }
        List<OrderLogDTO> msg = manageLogMapper.getMSG(start);
        if (msg.isEmpty()){
            return Result.success("没有日志");
        }else {
            return Result.success(msg);
        }
    }
    //返回訂單 log 的总行数
    @RequestMapping("/api/log/order/count")
    public Result count(){
        int count = manageLogMapper.count();
        return Result.success(count);
    }
}
