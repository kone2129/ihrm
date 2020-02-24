package com.ihrm.demo;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * 创建token测试类
 */
public class CreatJwtTest {

    /**
     * 通过jwt创建token字符串
     * @param args
     */
    public static void main(String[] args) {
       JwtBuilder jwtBuilder = Jwts.builder().setId("1111").setSubject("kones")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"siyao")
               .claim("userName","kone.wang")
               .claim("friends", new String[]{"111", "2222", "33333"});
        String token  = jwtBuilder.compact();
        System.out.println(token);
    }
}
