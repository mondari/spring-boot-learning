# spring-boot-archetype

> 使用 maven-archetype-plugin 插件创建自定义 Spring Boot 项目原型
>

在 pom.xml 里添加 maven-archetype-plugin 插件

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-archetype-plugin</artifactId>
    <version>3.0.1</version>
</plugin>
```

在 pom.xml 文件所在目录执行命令

```bash
mvn archetype:create-from-project
cd target\generated-sources\archetype
mvn install
```

使用生成的项目原型来创建项目

```bash
mvn archetype:generate -DarchetypeCatalog=local
```

