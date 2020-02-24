package com.ihrm.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

/**
 * 解析token测试类
 */
public class PaserJwtTest {
    public static void main(String[] args) {
        String token="eyJhbGciOiJIUzI1NiJ9.eyJjb21wYW55SWQiOiIiLCJjb21wYW55TmFtZSI6IiIsImV4cCI6MTU4MjU1ODI5OX0.ri00eE38Iiqy--BAJGrDjLDcIjbn9iM4xslzh0hWnIw";
        Claims claims = Jwts.parser().setSigningKey("saas-ihrm").parseClaimsJws(token).getBody();
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getIssuedAt());
        System.out.println(claims.get("userName"));
        System.out.println(claims.get("friends"));
    }
}
