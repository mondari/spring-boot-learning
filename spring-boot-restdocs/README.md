# spring-boot-restdocs

> 本项目主要演示如何将swagger在线接口文档转换成离线HTML或PDF文档
> 本项目参考自[spring-swagger2markup-demo](https://github.com/Swagger2Markup/spring-swagger2markup-demo)

## 技术栈

- spring-boot-test：编写MockMvc测试用例，访问/v2/api-docs接口地址，将返回结果保存到swagger.json文件
- swagger2markup-maven-plugin：将swagger.json文件转换成AsciiDoc文件
- asciidoctor-maven-plugin：将AsciiDoc文件转换成HTML或PDF文件

