# spring-boot-demo-upload

传统 Java Web 项目如果要进行文件上传，需要注册一个 MultipartConfigElement 类到 Servlet 容器中（在 web.xml 文件 `<servlet>/<multipart config>`
中配置），但这些 Spring Boot 已经帮我们自动配置完成了，Spring Boot 的自动配置类是 `MultipartAutoConfiguration`。

待办：添加分片上传接口和SDK

参考：[Uploading Files](https://spring.io/guides/gs/uploading-files/)
