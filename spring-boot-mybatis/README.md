# spring-boot-mybatis

[TOC]



该 demo 主要演示以下功能：

- 使用 MyBatis-Plus 完成基本的增删查改
- 分别使用 MyBatis-Plus 和 Pagehelper 完成分页查询
- 手写 Mapper 完成批量插入、删除和更新功能，同时新增 Java 8 函数式编程的方法封装分页批量操作（增删改），应付海量数据导致 SQL 语句过长的问题
- 使用 `@Transactional` 注解实现事务功能（针对分页批量增删改）
- 使用 Alibaba Druid 监控数据源
- 使用 @Validated 相关注解进行方法入参校验和Bean校验
- 增加全局异常处理类
- MyBatis Generator 根据数据库表生成POLOs、Mapper、DAO
- 使用 JPA 初始化数据库
- [配置文件加密](#配置文件加密)（借助 druid-spring-boot-starter，jasypt-spring-boot-starter）



**在使用二级缓存前一定要认真考虑脏数据对系统的影响！！！**

MyBatis 的缓存有两级，一级缓存基于 SqlSession，二级缓存基于 SqlSessionFactory，如果开启了二级缓存，一级缓存结束后，就会缓存到二级。
一级缓存机制：
二级缓存机制：


读写缓存：不是同一实例，使用序列化来缓存。
只读缓存：同一实例，使用 Map 来缓存。
这个读写和只读跟能不能修改没有关系，只是区别是否是同一实例

Redis缓存的特点：

1. 支持数据结构多
2. 支持持久化
3. 支持分布式缓存

EhCache缓存的特点：

1. 缓存数据有内存和硬盘两级
2. 缓存数据在虚拟机重启的过程中自动写入到硬盘
3. 通过RMI支持分布式缓存，但是不方便



## 配置文件加密

目前只有 Alibaba Druid 数据源提供数据库密码加密功能，如果使用 HikariCP 等其它数据源，可以通过 jasypt-spring-boot-starter 的配置文件加密功能间接实现。

### [使用 Alibaba Druid 进行数据库密码加密](https://github.com/alibaba/druid/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)

在命令行中执行如下命令（这里以 `druid-1.1.13.jar` 为例，假设数据库密码为 `toor`）：

```bash
java -cp druid-1.1.13.jar com.alibaba.druid.filter.config.ConfigTools toor
```

输出

```bash
privateKey:MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAjFxen6rjwQNMoOZPIdhDu3qAwmTaf0d84y1W0+24VWYYga2qiKSksRmhE1EWkY9xWlbJdJ3AfnVe80U09cH7+QIDAQABAkAVKhByjvgMsIyrbk7cUZnU+SHVLhPsQUFJmBqRljTHW4YZ77UOX0MXUVH/5cdKufEkGmwfSm9sqPPKS48qgnApAiEA1kW8BMbs8/Yq+JB0HuwZi+3X/8Spbw69vLYnWWmYJrsCIQCnsd8gB40QQV9DFbPMXunNOgrUAVXphrY5IglAKdXu2wIgFCjfdbuZk6J3jIdaxYYFKUspZWEFZ/OFGnow3ZK3w/0CIH0ns28kM5O+NmrK97W/4J7agtpXZNc1QyrAzEam8pOhAiAWlUubYVEuQGebej9no16aox6cH9FU/5sH6KuMGdzdGw==
publicKey:MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIxcXp+q48EDTKDmTyHYQ7t6gMJk2n9HfOMtVtPtuFVmGIGtqoikpLEZoRNRFpGPcVpWyXSdwH51XvNFNPXB+/kCAwEAAQ==
password:ePDKV2Fo+FRnQvSmFIlDJlBgOad9jpB8XbiPAeMxtqinmHmpATxMNNXvG+YBXM93xGTE0haigD27OS8+FMc6CQ==
```

如上，结果包括 `privateKey` `publicKey` 和加密后的 `password` 。

接着到配置文件里配置

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
# 生成的加密后的密码（原密码 toor）
spring.datasource.password=ePDKV2Fo+FRnQvSmFIlDJlBgOad9jpB8XbiPAeMxtqinmHmpATxMNNXvG+YBXM93xGTE0haigD27OS8+FMc6CQ==
# 生成的公钥
public-key=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIxcXp+q48EDTKDmTyHYQ7t6gMJk2n9HfOMtVtPtuFVmGIGtqoikpLEZoRNRFpGPcVpWyXSdwH51XvNFNPXB+/kCAwEAAQ==
# 配置 connection-properties，启用加密，配置公钥。
spring.datasource.druid.connection-properties=config.decrypt=true;config.decrypt.key=${public-key}
# 启用ConfigFilter
spring.datasource.druid.filter.config.enabled=true
```

yml的配置如下

```yml
# 生成的公钥
public-key: MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIxcXp+q48EDTKDmTyHYQ7t6gMJk2n9HfOMtVtPtuFVmGIGtqoikpLEZoRNRFpGPcVpWyXSdwH51XvNFNPXB+/kCAwEAAQ==
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test
    username: root
    # 生成的加密后的密码（原密码 toor）
    password: ePDKV2Fo+FRnQvSmFIlDJlBgOad9jpB8XbiPAeMxtqinmHmpATxMNNXvG+YBXM93xGTE0haigD27OS8+FMc6CQ==
    druid:
    # 配置 connection-properties，启用加密，配置公钥。
      connection-properties: config.decrypt=true;config.decrypt.key=${public-key}
      # 启用ConfigFilter
      filter:
        config:
          enabled: true
```

### [使用 jasypt 进行配置文件加密](https://github.com/ulisesbocchio/jasypt-spring-boot)

在命令行中执行如下命令（这里以 `jasypt-1.9.2.jar` 为例，假设数据库密码为 `toor`，加密密钥为 `password`）：

```bash
java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="toor" password=20190518 algorithm=PBEWithMD5AndDES
```

输出

```bash
----ENVIRONMENT-----------------

Runtime: Oracle Corporation Java HotSpot(TM) 64-Bit Server VM 11+28



----ARGUMENTS-------------------

input: toor
password: password
algorithm: PBEWithMD5AndDES



----OUTPUT----------------------

lefIojh5L05Z8PPJnwYUGQ==
```

如上，加密后的数据库密码为 `lefIojh5L05Z8PPJnwYUGQ==`

接着到配置文件里配置

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
# 生成的加密后的密码（原密码 toor）
spring.datasource.password=ENV(lefIojh5L05Z8PPJnwYUGQ==)
```

yml的配置如下

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test
    username: root
    # 生成的加密后的密码（原密码 toor）
    password: ENV(lefIojh5L05Z8PPJnwYUGQ==)
```

由于不能把密钥配置到配置文件里，所以这里使用 Java 代码配置密钥（同样存在安全风险）：

```java
	@Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("password");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
```

注：官方推荐使用 JVM系统属性、命令行参数或环境变量方式设置 `jasypt.encryptor.password` 来传递密钥。