package com.mondari.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AOPConfig {

    private static final Logger LOG = LoggerFactory.getLogger(AOPConfig.class);

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object doAround(final ProceedingJoinPoint pjp) throws Throwable {



        LOG.info("-----Before-----");

        // 切点表达式示例：execution(public java.lang.String com.demo.controller.HelloWorldController.say(java.lang.String))
        System.out.println("切点表达式：" + pjp.toLongString());

        // 切点的签名示例：public java.lang.String com.demo.controller.HelloWorldController.say(java.lang.String)
        System.out.println("切点的签名：" + pjp.getSignature().toLongString());

        // 切点描述的对象：HelloWorldController
        System.out.println("切点目标对象：" + pjp.getTarget().toString());

        // 获取切点描述对象的方法的参数
        System.out.println("切点的传参：" + Arrays.toString(pjp.getArgs()));

        long startTime = System.currentTimeMillis();
        // 执行切点
        Object o = pjp.proceed();
        LOG.info("耗时 : {} ms", (System.currentTimeMillis() - startTime));
        LOG.info("----Finished----");

        return o;
    }
}