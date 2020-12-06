package com.mondari.proxy.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * <p>
 *
 * </p>
 *
 * @author limondar
 * @date 2020/12/6
 */
public class MyAdviceInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("——调用前处理——");
        Object ret = invocation.proceed();
        System.out.println("——调用后处理——");
        return ret;
    }
}
