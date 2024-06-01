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
        try {
            addressMapper.deleteAddress(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error("删除失败");
        }

    }


}
