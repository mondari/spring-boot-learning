# spring-boot-demo-security-oauth2

[toc]

## 使用方法

1. 请求 access_token：
   
   1. password授权方式（以user身份授权）：
   
      POST http://localhost:8080/oauth/token?username=user&password=user&grant_type=password&client_id=passwordClientId&client_secret=passwordSecret&scope=any
   
      ```json
      {
          "access_token": "ebe06ce2-8488-4550-bb96-c38d0ac2bcab",
          "token_type": "bearer",
          "refresh_token": "323ec828-e6da-4f66-a694-84593d0b3ba7",
          "expires_in": 43199,
          "scope": "any"
      }
      ```
   
      
   
   2. client_credentials授权方式（以该方式授权是没有身份信息的）：
   
      POST http://localhost:8080/oauth/token&grant_type=client_credentials&client_id=userClientId&client_secret=user&scope=any
   
      ```json
      {
          "access_token": "2b31c7b9-eb50-4fa0-a2be-52b5e924d02f",
          "token_type": "bearer",
          "expires_in": 43170,
          "scope": "any"
      }
      ```
   
      
   
2. 访问资源（以client_credentials授权方式获取的access_token访问）：

   1. GET http://localhost:8080/admin/hello?access_token=fc59a990-3adc-4b7d-9177-40f310487d35

      ```json
      {
          "error": "access_denied",
          "error_description": "Access is denied"
      }
      ```

      

   2. GET http://localhost:8080/user/hello?access_token=fc59a990-3adc-4b7d-9177-40f310487d35

      ```json
      {
          "error": "access_denied",
          "error_description": "Access is denied"
      }
      ```
      
      
      
   3. GET http://localhost:8080/hello?access_token=fc59a990-3adc-4b7d-9177-40f310487d35

      ```json
      hello
      ```

   

3. 使用 refresh_token 刷新 access_token
   POST http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=f9937e92-8851-4279-af34-42f5c00256f4&client_id=userClientId&client_secret=user

## 后续优化

用户信息从数据库中拿，参考Gitee开源项目Pig

## 参考

该项目参考：

1. 知名博主“江南一点雨”的 javaboy-video-samples 项目：https://github.com/lenve/javaboy-video-samples/tree/master/%E7%AC%AC%2010%20%E7%AB%A0%20Spring%20Boot%20%E5%AE%89%E5%85%A8%E7%AE%A1%E7%90%86/oauth2
2. http://blog.didispace.com/spring-security-oauth2-xjf-1/


