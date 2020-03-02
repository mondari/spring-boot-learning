package com.mondari;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SecurityJwtApplicationTest {

    @Test
    public void contextLoads() {

        String username = "admin";
        String password = "admin";
        String roles = "ROLE_ADMIN";
        String base64EncodedSecretKey = "secret";
        // 声明
        Claims claim = Jwts.claims();
        claim.put("自定义Key", "自定义value");
        claim.put("username", username);
        claim.put("password", password);
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
                .signWith(SignatureAlgorithm.HS512, base64EncodedSecretKey)
                .compact();
        System.out.println("JWT Token: " + jwtToken);

        // JWT Token 解析
        Claims claims = Jwts.parser().setSigningKey(base64EncodedSecretKey).parseClaimsJws(jwtToken).getBody();
        try {
            System.out.println(new ObjectMapper().writeValueAsString(claims));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
