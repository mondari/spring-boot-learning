# spring-boot-demo-security

[toc]

## 两种认证方式

### 表单登录认证

浏览器表单登录认证页面：

![image-20200301160507701](README.assets/image-20200301160507701.png)

浏览器请求如图：

![image-20200301170323993](README.assets/image-20200301170323993.png)

通过Postman表单登录：

![image-20200301170037015](README.assets/image-20200301170037015.png)

### HTTP Basic 认证

浏览器认证界面：

![image-20200301192820947](README.assets/image-20200301192820947.png)

浏览器请求如图：

![image-20200301192956864](README.assets/image-20200301192956864.png)

Postman认证方法：

![image-20200301171444373](README.assets/image-20200301171444373.png)

注意是GET请求而不是POST，路径是 /hello 而不是 /login

参考：https://blog.csdn.net/wangb_java/article/details/86502166

