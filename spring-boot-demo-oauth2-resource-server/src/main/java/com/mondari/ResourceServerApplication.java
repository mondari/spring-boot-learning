package com.mondari;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author limondar
 * @date 2020/3/6
 */
@Slf4j
@RestController
@SpringBootApplication
public class ResourceServerApplication {

    /**
     * 资源服务器通过该接口根据access_token去认证
     */
    @Autowired
    ResourceServerTokenServices remoteTokenServices;

    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class);
    }

    /**
     * 资源服务器管理的资源
     *
     * @return
     */
    @GetMapping("/resource")
    public String resource() {
        log.info("someone visits resource");
        return "resource";
    }

    /**
     * 资源服务器未管理的资源
     *
     * @return
     */
    @GetMapping(value = {"/", "/hello", "/index"})
    public String index() {
        return "hello world";
    }


}
