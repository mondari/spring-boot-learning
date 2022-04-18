# spring-boot-demo-websocket

启动服务，打开浏览器，打开 [WebSocket在线测试工具](http://www.websocket-test.com/) ，连接 **wss://localhost:8443/chat/{你的昵称或用户ID}** 或 **wss:
//localhost:8443/chatroom/{房间号}/{你的昵称或用户ID}** 即可进行在线聊天

## 功能简介

- [x] 多人聊天室和群组聊天室两大功能
- [x] 在线人数功能
- [x] ServerEndpoint单例（默认情况下JavaEE的ServerEndpoint是多实例）
- [x] 解决不能Autowired的问题
- [x] 解决使用SpringConfigurator报错的问题：`Failed to find the root WebApplicationContext. Was ContextLoaderListener not used?`
- [x] ServerSocket单例情况下消息群发
- [x] WSS安全连接

## 配置WSS协议

啥是wss协议？wss全称是websocket secure，也就是 websocket 协议的加密版本，类似与https与http的关系。

配置WSS协议起始就是配置HTTPS，因为WebSocket是建立在HTTP的基础上的。配置步骤如下：

1. 生成自签名证书

   ```shell
   keytool -genkeypair -alias tomcat -keyalg RSA -keystore D:\keystore.jks
   ```

2. application.yml配置文件配置

   ```yml
   server:
     port: 8443
     ssl:
       enabled: true
       key-store: classpath:keystore.jks
       key-store-password: 123456
       key-password: 123456
       key-alias: tomcat
   
   ```

   这里指定项目的端口为8443，也就是SSL加密的端口，支持https，同样也支持wss
   
3. 测试

   在Controller中添加以下代码：

   ```java
   @GetMapping("hello")
   public String hello() {
       return "hello";
   }
   ```

   启动应用，访问 https://localhost:8443/hello ，浏览器提示证书不安全，忽略掉后接口返回“hello”，说明HTTPS配置成功，也就是说WSS配置成功

## 参考

https://spring.io/blog/2013/05/23/spring-framework-4-0-m1-websocket-support

https://blog.csdn.net/u012977315/article/details/84944708