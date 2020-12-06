package com.mondari.proxy.dynamic.jdk;

import com.mondari.proxy.TargetInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <p>
 * JDK 动态代理 - 需要实现 InvocationHandler 接口
 * </p>
 *
 * @author limondar
 * @date 2020/12/6
 */
public class MyInvocationHandler implements InvocationHandler {

    TargetInterface target;

    public MyInvocationHandler(TargetInterface target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("——调用前处理——");
        target.action();
        System.out.println("——调用后处理——");
        return null;
    }
}
