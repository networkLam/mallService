package com.lam.Utils;

import com.lam.pojo.TokenUserInfo;

public class UserTheadLocal {
    //储存用户信息
    private static ThreadLocal<TokenUserInfo> userThread = new ThreadLocal<>();
//    设置用户信息
    public static void set(TokenUserInfo tokenUserInfo){
        userThread.set(tokenUserInfo);
    }
//获取用户信息
    public static TokenUserInfo get(){
        return userThread.get();
    }
//移除用户信息
    public static void remove(){
        userThread.remove();
    }

}
