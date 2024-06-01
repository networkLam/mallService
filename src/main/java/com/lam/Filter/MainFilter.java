package com.lam.Filter;

import com.alibaba.fastjson.JSONObject;
import com.lam.Utils.JwtUtil;
import com.lam.Utils.UserTheadLocal;
import com.lam.pojo.Result;
import com.lam.pojo.TokenUserInfo;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class MainFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        放行前
//        System.out.println("放行前的操作");

        HttpServletRequest req = (HttpServletRequest) servletRequest;//request object
        HttpServletResponse resp = (HttpServletResponse) servletResponse; //response object

        //1.get request url
        String requestURI = req.getRequestURI();
        //2.determine request is as include login word?
        log.info("请求的url是:{}", requestURI);
        //放行登录 注册 浏览图片
        if (requestURI.contains("login") || requestURI.contains("register")|| requestURI.contains("image")) {
            log.info("登录操作或注册操作，放行...");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        //3.get request header the token
        String token = req.getHeader("token");
        if (!StringUtils.hasLength(token)) {
            log.info("请求头token为空，返回未登录信息");
            Result error = Result.error("NOT_LOGIN");
            //manual
            //convert object type to json type
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin); //return browser
            return;

        }
        //解析token
        try {
            Claims userInfo = JwtUtil.jwtParser(token);
            String name = userInfo.get("name", String.class);
            String phone = userInfo.get("phone", String.class);
            Integer id = userInfo.get("id",Integer.class);
            String authorization = userInfo.get("authorization",String.class);
            TokenUserInfo tokenUserInfo = new TokenUserInfo();
            tokenUserInfo.setName(name);//设置姓名
            tokenUserInfo.setPhone(phone);//手机号
            tokenUserInfo.setId(id);//id
            tokenUserInfo.setAuthorization(authorization);//表明身份的字段
            UserTheadLocal.set(tokenUserInfo);//往线程里面塞数据

        } catch (Exception e) {
//            e.printStackTrace();
            log.info("解析出错,返回未登录的错误信息");
            Result error = Result.error("NOT_LOGIN");
            //manual
            //convert object type to json type
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin); //return browser
            return;
        }
//        放行后的操作
//        System.out.println("放行后的操作");
        //放行
        log.info("令牌合法，放行。");
        filterChain.doFilter(servletRequest, servletResponse);
//        System.out.println("这是最后输出的吗？");
        UserTheadLocal.remove();//最后移除线程数据
    }
}
