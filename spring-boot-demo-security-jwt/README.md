# spring-boot-demo-security-jwt

[toc]

## 运行

启动项目，

通过 IDEA HTTP Request 请求：

```http
POST http://localhost:8080/oauth/token?grant_type=password&username=user&password=password
Content-Type: application/x-www-form-urlencoded
Authorization: Basic appId appSecret
```

返回结果：

```json
{
  "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1ODcwMTA5MjgsInVzZXJfbmFtZSI6InVzZXIiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiOGZlZWVhZDAtMTM1Zi00YTZjLThkYzAtYTM5YjAwOTZhODFmIiwiY2xpZW50X2lkIjoiYXBwSWQiLCJzY29wZSI6WyJvcGVuaWQiXX0.WgmJGLOWO3wnLg2x9HSZhM5j1pZuiufcOgeTAz98bnY",
  "token_type": "bearer",
  "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyIiwic2NvcGUiOlsib3BlbmlkIl0sImF0aSI6IjhmZWVlYWQwLTEzNWYtNGE2Yy04ZGMwLWEzOWIwMDk2YTgxZiIsImV4cCI6MTU4OTU1OTcyOCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6IjcxZTcwM2RkLTUwNmEtNDY3My1hMjI4LTU2OTE1OTRjMjA0YSIsImNsaWVudF9pZCI6ImFwcElkIn0.lp8YMqm80yfnj66iCZjBEhh4s67UY9rnee6_i6W4KXc",
  "expires_in": 43193,
  "scope": "openid",
  "jti": "8feeead0-135f-4a6c-8dc0-a39b0096a81f"
}
```



## 参考：

1. https://github.com/spring-cloud-samples/authserver
2. https://mrbird.cc/Spring-Security-OAuth2-Token-Config.html
3. https://mrbird.cc/Spring-Security-OAuth2-SSO.html

