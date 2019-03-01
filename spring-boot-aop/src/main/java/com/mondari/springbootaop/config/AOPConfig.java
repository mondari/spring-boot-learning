package com.mondari.springbootaop.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AOPConfig {
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object doAround(final ProceedingJoinPoint pjp) throws Throwable {

        // 切点表达式：execution(public java.lang.String com.demo.controller.HelloWorldController.say(java.lang.String))
        String pjpString = pjp.toLongString();

        // 切点的签名：public java.lang.String com.demo.controller.HelloWorldController.say(java.lang.String)
        String signature = pjp.getSignature().toLongString();

        // 切点描述的对象：HelloWorldController
        Object target = pjp.getTarget();

        // 获取切点描述对象的方法的参数
        Object[] args = pjp.getArgs();

        // 执行切点
        Object o = pjp.proceed();

        return o;
    }
}