package com.example.chatserver.common.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.secretKeyRt}")
    private String secretKeyRt;
    @Value("${jwt.expirationRt}")
    private int expirationRt;
    private Key SECRET_KEY;
    private Key RT_SECRET_KEY;

    @PostConstruct
    public void init() {
        // Base64로 인코딩된 키를 디코딩한 후 SECRET_KEY 로 변환
        SECRET_KEY = new SecretKeySpec(java.util.Base64.getDecoder().decode(secretKey), SignatureAlgorithm.HS512.getJcaName());
        RT_SECRET_KEY = new SecretKeySpec(java.util.Base64.getDecoder().decode(secretKeyRt), SignatureAlgorithm.HS512.getJcaName());
    }

    public String createToken(String email, String role){
//        claims는 사용자정보(페이로드 정보)
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)  //생성시간
                .setExpiration(new Date(now.getTime() + expiration*60*1000L))  //만료시간 : 30분
                .signWith(SECRET_KEY)
                .compact();
        return token;
    }


    public String createRefreshToken(String email, String role){
//        claims는 사용자정보(페이로드 정보)
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)  //생성시간
                .setExpiration(new Date(now.getTime() + expirationRt*60*1000L))  //만료시간 : 30분
                .signWith(RT_SECRET_KEY)
                .compact();
        return token;
    }
}
