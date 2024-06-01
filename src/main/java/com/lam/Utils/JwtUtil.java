package com.lam.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
@RestController
public class JwtUtil {
    private static final String KEY = "lam!123";
    private static final Long TIME =43200000L;//过期时间，单位毫秒
    public static String jwtBuilder(HashMap<String,Object> Claims){
       return Jwts.builder().setClaims(Claims).signWith(SignatureAlgorithm.HS256,KEY).setExpiration(new Date(System.currentTimeMillis()+TIME)).compact();
    }

    public static Claims jwtParser(String token){
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
