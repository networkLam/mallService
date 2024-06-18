package com.lam.Controller;

import com.lam.Utils.UserTheadLocal;
import com.lam.mapper.AddressMapper;
import com.lam.pojo.Address;
import com.lam.pojo.Result;
import com.lam.pojo.TokenUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class AddressController {
    @Autowired
    private AddressMapper addressMapper;

    //用户地址添加接口测试成功
    @PostMapping("/api/address/add")
    public Result addAddress(@RequestBody Address address) {
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        address.setUid(tokenUserInfo.getId());  //该地址所属用户id
        try {
            addressMapper.addAddress(address);
            return Result.success("添加地址成功");
        } catch (Exception e) {
            System.out.println("添加地址出现错误");
            System.out.println(e.getMessage());
            return Result.error("error");
        }
    }

    //    删除一个地址
    @GetMapping("/api/address/del")
    public Result deleteAddress(Integer id) {
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        try {
            addressMapper.deleteAddress(id,tokenUserInfo.getId());
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error("删除失败");
        }

    }

    //用户浏览属于自己的地址
    @GetMapping("/api/address/browser")
    public Result browserAddress(){
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        List<Address> browser = addressMapper.browser(tokenUserInfo.getId());
        return Result.success(browser);
    }

    //用户更新自己的地址
    @PostMapping("/api/address/update")
    public Result updateAddress(@RequestBody Address address){
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        address.setUid(tokenUserInfo.getId());//获取用户id
        try {
            addressMapper.updateAddress(address);
            return Result.success("更新成功");
        }catch (Exception e){
            log.info("更新失败，请检查");
            return Result.error("更新失败");
        }
    }

    //根据地址ID返回地址的信息
    @GetMapping("/api/address/query")
    public Result queryAddress(Integer addId){
        Address address = addressMapper.queryAddId(addId);
        return Result.success(address);
    }


}
