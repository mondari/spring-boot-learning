package com.mondari.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * AOP切面配置类
 */
@Aspect
@Component
public class AopConfig {

    private static final Logger LOG = LoggerFactory.getLogger(AopConfig.class);

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object doAround(final ProceedingJoinPoint pjp) throws Throwable {

        LOG.info("-----Before-----");

        // 切点表达式：execution(public java.lang.String com.mondari.AopApplication.hello())
        System.out.println("切点表达式：" + pjp.toLongString());

        // 切点的签名：public java.lang.String com.mondari.AopApplication.hello()
        System.out.println("切点的签名：" + pjp.getSignature().toLongString());

        // 切点目标对象：com.mondari.AopApplication$$EnhancerBySpringCGLIB$$4f6e9dba@7f6a1d1f
        System.out.println("切点目标对象：" + pjp.getTarget().toString());

        // 切点的传参：[]
        System.out.println("切点的传参：" + Arrays.toString(pjp.getArgs()));

        // 开始时间
        long startTime = System.currentTimeMillis();

        // 执行切点
        Object o = pjp.proceed();

        // 耗时
        LOG.info("耗时 : {} ms", (System.currentTimeMillis() - startTime));
        LOG.info("----Finished----");

        // 返回结果
        return o;
    }
}