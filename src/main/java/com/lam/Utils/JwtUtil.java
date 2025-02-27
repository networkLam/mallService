package com.lam.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
@Component
public class JwtUtil {
    private static final String KEY = "lam!123";
    private static final Long TIME =43200000L;//过期时间，单位毫秒
    public static String jwtBuilder(HashMap<String,Object> Claims){
        // 打印过期时间和当前时间进行对比
        Date now = new Date();
        Date expiration = new Date(now.getTime() + TIME);
        System.out.println("当前时间: " + now);
        System.out.println("过期时间: " + expiration);
       return Jwts.builder().setClaims(Claims).signWith(SignatureAlgorithm.HS256,KEY).setExpiration(expiration).compact();
    }

    public static Claims jwtParser(String token){
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
