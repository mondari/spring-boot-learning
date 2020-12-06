package com.mondari.proxy.dynamic.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * <p>
 * CGlib 动态代理 - 需要实现 MethodInterceptor 接口
 * </p>
 *
 * @author limondar
 * @date 2020/12/6
 */
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("——调用前处理——");
        Object result = methodProxy.invokeSuper(obj, args);
        System.out.println("——调用后处理——");
        return result;
    }
}
