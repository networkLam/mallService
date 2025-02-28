package com.lam.security;

import com.alibaba.fastjson.JSONObject;
import com.lam.Service.UserDetailServiceImp;
import com.lam.Utils.JwtUtil;
import com.lam.Utils.UserTheadLocal;
import com.lam.pojo.Result;
import com.lam.pojo.TokenUserInfo;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Configuration
@Slf4j
public class JWTFilterChainConfig extends OncePerRequestFilter {


    @Autowired
    private UserDetailServiceImp userDetailServiceImp;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {

        HttpSession httpSession = ((HttpServletRequest) request).getSession();


        //1.get request url
        String requestURI = ((HttpServletRequest) request).getRequestURI();

        //2.determine request is as include login word?
        log.info("请求的url是:{}", requestURI);
        //放行登录 注册 浏览图片 upload是暫時放行的
        if (requestURI.contains("login") || requestURI.contains("register") || requestURI.contains("image") || requestURI.contains("upload") || requestURI.contains("/product/page/") || requestURI.contains("/ws")) {
            log.info("操作放行...");
            httpSession.setAttribute("user", "default");
            filterChain.doFilter(request, response);
            return;
        }
        //3.get request header the token
        String token = ((HttpServletRequest) request).getHeader("token");
        if (!StringUtils.hasLength(token)) {
            token = ((HttpServletRequest) request).getParameter("token");
        }
        if (!StringUtils.hasLength(token)) {
            log.info("请求头token为空，返回未登录信息");
            Result error = Result.error("NOT_LOGIN");
            //manual
            //convert object type to json type
            String notLogin = JSONObject.toJSONString(error);
            ((HttpServletResponse) response).getWriter().write(notLogin); //return browser
            return;

        }
        //解析token
        try {
            Claims userInfo = JwtUtil.jwtParser(token);
            // 打印过期时间和当前时间进行对比
//            Date expirationTime = userInfo.getExpiration();
//            System.out.println("Token 过期时间: " + expirationTime);
//            System.out.println("当前时间: " + new Date());

//            String name = userInfo.get("name", String.class);
//            String phone = userInfo.get("phone", String.class);
            Integer id = userInfo.get("id", Integer.class);
//            String authorization = userInfo.get("authorization", String.class);
            TokenUserInfo tokenUserInfo = new TokenUserInfo();
//            tokenUserInfo.setName(name);//设置姓名
//            tokenUserInfo.setPhone(phone);//手机号
            tokenUserInfo.setId(id);//id
//            tokenUserInfo.setAuthorization(authorization);//表明身份的字段
            UserTheadLocal.set(tokenUserInfo);//往线程里面塞数据
            httpSession.setAttribute("user", id); //将用户ID作为唯一值
            /*
             *将数据设置到内存中
             * */
            UserDetails userDetails = userDetailServiceImp.loadUserByUsername(id.toString()); //查找用户ID，根据用户ID返回相关信息
            System.out.println("用户信息如下");
            System.out.println(userDetails);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
//            e.printStackTrace();
            log.error("解析Token时发生异常: {}", e.getMessage(), e); // 增加详细的异常日
            log.info("解析出错,返回未登录的错误信息");
            Result error = Result.error("NOT_LOGIN");
            //manual
            //convert object type to json type
            String notLogin = JSONObject.toJSONString(error);
            ((HttpServletResponse) response).getWriter().write(notLogin); //return browser
            return;
        }
//        放行后的操作
//        System.out.println("放行后的操作");
        //放行
        log.info("令牌合法，放行。");
        filterChain.doFilter(request, response);
//        System.out.println("这是最后输出的吗？");
        UserTheadLocal.remove();//最后移除线程数据
    }
}
