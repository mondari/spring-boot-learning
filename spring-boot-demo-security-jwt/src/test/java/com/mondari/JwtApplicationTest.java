package com.mondari;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class JwtApplicationTest {

    private final String signKey = "secret";

    @Test
    public void contextLoads() {

        String username = "admin";
        String roles = "ROLE_ADMIN";
        // 声明
        Claims claim = Jwts.claims();
        claim.put("自定义Key", "自定义value");
        claim.put("username", username);
        claim.put("roles", roles);
        claim.setId("JWT ID");
        claim.setSubject("主题");
        claim.setAudience("受众");
        claim.setIssuedAt(null);// 创建时间
        claim.setExpiration(null);// 过期时间
        claim.setNotBefore(null);// 生效时间

        // JWT Token 生成
        String jwtToken = Jwts.builder()
                .setClaims(claim)
                .signWith(SignatureAlgorithm.HS512, signKey)
                .compact();
        System.out.println("JWT Token: " + jwtToken);

        // JWT Token 解析
        System.out.println(parseJwtToken(signKey, jwtToken));
        System.out.println(decodeJwtToken(jwtToken));
    }

    /**
     * 使用第三方Jar包解析
     *
     * @param key
     * @param jwtToken
     * @return
     */
    @SneakyThrows
    private String parseJwtToken(String key, String jwtToken) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken).getBody();
        return new ObjectMapper().writeValueAsString(claims);
    }

    /**
     * 使用Spring自带工具类解析
     *
     * @param token
     * @return
     */
    @SneakyThrows
    private String decodeJwtToken(String token) {
        return JwtHelper.decode(token).getClaims();
    }

}
