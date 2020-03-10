# spring-boot-demo-websocket-wss

wss，也就是websocket secure，websocket 的加密版本，类似与https与http的关系。

## 启动

启动服务，打开浏览器，打开[WebSocket在线测试工具](http://ws.douqq.com/)，连接**wss://localhost:8443/chat/{你的昵称}**即可进行在线聊天

## 如何配置WSS

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

3. 配置类配置tomcat

   ```java
   @Bean
       public ServletWebServerFactory servletContainer() {
           TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
    
           tomcat.addAdditionalTomcatConnectors(createSslConnector());
           return tomcat;
       }
    
       private Connector createSslConnector() {
           Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
           connector.setScheme("http");
           //Connector监听的http的端口号
           connector.setPort(8080);
           connector.setSecure(false);
           //监听到http的端口号后转向到的https的端口号
           connector.setRedirectPort(8443);
           return connector;
       }
    
       /**
        * 创建wss协议接口
        *
        * @return
        */
       @Bean
       public TomcatContextCustomizer tomcatContextCustomizer() {
           System.out.println("init");
           return new TomcatContextCustomizer() {
               @Override
               public void customize(Context context) {
                   System.out.println("init   customize");
                   context.addServletContainerInitializer(new WsSci(), null);
               }
    
           };
       }
   
   ```

   

## 参考

https://blog.csdn.net/u012977315/article/details/84944708