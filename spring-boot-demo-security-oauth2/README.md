# spring-boot-demo-security-oauth2

[toc]

## 使用方法

1. 请求 access_token：
   
   1. password密码模式（user身份的密码）：
   
      POST http://localhost:8080/oauth/token?username=user&password=user&grant_type=password&client_id=appId&client_secret=appSecret&scope=any
   
      ```json
      {
          "access_token": "ebe06ce2-8488-4550-bb96-c38d0ac2bcab",
          "token_type": "bearer",
          "refresh_token": "323ec828-e6da-4f66-a694-84593d0b3ba7",
          "expires_in": 43199,
          "scope": "any"
      }
      ```
   
      
   
   2. client_credentials客户端模式（以该方式授权是没有身份信息的）：
   
      POST http://localhost:8080/oauth/token&grant_type=client_credentials&client_id=appId&client_secret=appSecret&scope=any
   
      ```json
      {
          "access_token": "2b31c7b9-eb50-4fa0-a2be-52b5e924d02f",
          "token_type": "bearer",
          "expires_in": 43170,
          "scope": "any"
      }
      ```
   
      
      
   3. authorization_code授权码模式：
   
      1. GET http://localhost:8080/oauth/authorize?response_type=code&client_id=appId&redirect_uri=http://mrbird.cc&scope=any&state=hello
   
      2. 浏览器会自动跳转到 http://localhost:8080/login 页面提示输入用户名和密码
   
      3. 输入正确的用户名和密码后，会跳转到 https://mrbird.cc/?code=KsTKo9&state=hello
   
      4. POST http://localhost:8080/oauth/token?grant_type=authorization_code&code=KsTKo9&client_id=appId&client_secret=appSecret&redirect_uri=http://mrbird.cc&scope=any
   
         ```json
         {
             "access_token": "3c4d4a10-617c-43dd-8be3-d7455a4c5feb",
             "token_type": "bearer",
             "refresh_token": "0f5ec9d2-99d4-4d1b-ba32-6c88934bc626",
             "expires_in": 43199,
             "scope": "any"
         }
         ```
   
         
   
2. 访问资源（以user身份获取的access_token访问）：

   1. GET http://localhost:8080/res/admin/hello?access_token=fc59a990-3adc-4b7d-9177-40f310487d35

      ```json
      {
          "error": "access_denied",
          "error_description": "Access is denied"
      }
      ```

      

   2. GET http://localhost:8080/res/user/hello?access_token=fc59a990-3adc-4b7d-9177-40f310487d35

      ```json
      hello user
      ```
      
      
      
   
3. 使用 refresh_token 刷新 access_token
   POST http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=f9937e92-8851-4279-af34-42f5c00256f4&client_id=userClientId&client_secret=user

## 参考

该项目参考：

1. 知名博主“江南一点雨”的 javaboy-video-samples 项目：https://github.com/lenve/javaboy-video-samples/tree/master/%E7%AC%AC%2010%20%E7%AB%A0%20Spring%20Boot%20%E5%AE%89%E5%85%A8%E7%AE%A1%E7%90%86/oauth2
2. http://blog.didispace.com/spring-security-oauth2-xjf-1/
3. https://mrbird.cc/Spring-Security-OAuth2-Guide.html


