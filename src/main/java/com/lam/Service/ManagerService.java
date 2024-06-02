package com.lam.Service;

import com.lam.mapper.ManageMapper;
import com.lam.pojo.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ManagerService {
    @Autowired
    private ManageMapper manageMapper;
//    register API
    public int Register(String phone,String pwd){
        List<Manager> result = manageMapper.checkPhone(phone);
        if(!result.isEmpty()){
            return 0;
        }
        Manager manager = new Manager();
        LocalDateTime localDateTime = LocalDateTime.now();
        manager.setPhone(phone);
        manager.setM_pwd(pwd);
        manager.setGender("other");
        manager.setEntry_time(localDateTime);
        manager.setName("defaultAdministrator");
//        注册成功应该返回1
        try{
            manageMapper.RegisterAdministrator(manager);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 1;
    }
//update api 此接口應該是用戶信息更新的，但不知道爲何沒有使用。
    public Boolean updateInfo(Manager manager){
        if(manager.getPhone().length() !=11){
            return false;
        }
        int flag = manageMapper.updateInfo(manager);
        return flag >= 1;
    }
}
