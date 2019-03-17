# spring-boot-docker
参考 [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)

## 使用方法：

1. 运行 `mvn package dockerfile:build` 命令将项目打成docker镜像

2. 运行 `docker run -p 8080:8080 -t com.mondari/spring-boot-docker` 命令运行容器
3. 在浏览器打开 [localhost:8080](localhost:8080) ，页面显示 `Hello Docker World` 。

PS：

1. 如果您在 `Windows` 或 `Mac` 上使用的是 `boot2docker` ，请将 `localhost` 改成 docker 主机的 IP 地址
2. 使用 `docker run -e "SPRING_PROFILES_ACTIVE=prod" -p 8080:8080 -t com.mondari/spring-boot-docker` 命令可以指定启动时使用的配置文件

## Docker 基本命令：

`docker ps` 查看运行的容器

`docker stop CONTAINER_ID` 停止运行的容器

`docker rm CONTAINER_ID` 删除容器

`docker rmi IMAGE_ID` 删除镜像
