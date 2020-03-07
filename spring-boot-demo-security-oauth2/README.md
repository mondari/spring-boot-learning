# spring-boot-demo-security-oauth2

[toc]

## 使用方法

1. 请求 access_token（以user身份）：
   POST http://localhost:8080/oauth/token?username=user&password=user&grant_type=password&client_id=userClientId&client_secret=userClientSecret&scope=all
   
2. 访问资源：

   1. GET http://localhost:8080/admin/hello?access_token=fc59a990-3adc-4b7d-9177-40f310487d35

   提示权限不足：access_denied

   2. GET http://localhost:8080/hello?access_token=fc59a990-3adc-4b7d-9177-40f310487d35

   返回：hello

3. 使用 refresh_token 刷新 access_token
   POST http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=f9937e92-8851-4279-af34-42f5c00256f4&client_id=clientId&client_secret=123

## 参考

该项目参考：知名博主“江南一点雨”的 javaboy-video-samples 项目：https://github.com/lenve/javaboy-video-samples/tree/master/%E7%AC%AC%2010%20%E7%AB%A0%20Spring%20Boot%20%E5%AE%89%E5%85%A8%E7%AE%A1%E7%90%86/oauth2


