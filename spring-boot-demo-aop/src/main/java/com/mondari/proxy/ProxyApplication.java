package com.mondari.proxy;

import com.mondari.proxy.aop.MyAdviceInterceptor;
import com.mondari.proxy.dynamic.cglib.MyMethodInterceptor;
import com.mondari.proxy.dynamic.jdk.MyInvocationHandler;
import com.mondari.proxy.statics.ProxyObject;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * 代理模式（静态代理、动态代理）
 */
public class ProxyApplication {
    public static void main(String[] args) {
        // 目标对象
        TargetObject targetObject = new TargetObject();

        System.out.println("\n静态代理：");
        // 静态代理对象
        ProxyObject staticProxyObject = new ProxyObject(targetObject);
        // 执行代理对象的动作其实就是执行目标对象的动作，并且在目标对象的基础上做增强，不做修改
        staticProxyObject.action();

        System.out.println("\nJDK 动态代理：");
        // JDK 动态代理对象
        TargetInterface jdkDynamicProxyObject = (TargetInterface) Proxy.newProxyInstance(ProxyApplication.class.getClassLoader(),
                new Class[]{TargetInterface.class}, new MyInvocationHandler(targetObject));
        jdkDynamicProxyObject.action();


        // CGlib 动态代理对象（参考：https://www.cnblogs.com/wyq1995/p/10945034.html）
        System.out.println("\nCGlib 动态代理：");
        // 创建Enhancer对象，类似于JDK动态代理的Proxy类，下一步就是设置几个参数
        Enhancer enhancer = new Enhancer();
        // 设置目标类的字节码文件
        enhancer.setSuperclass(TargetObject.class);
        // 设置回调函数
        enhancer.setCallback(new MyMethodInterceptor());
        TargetObject cglibDynamicProxyObject = (TargetObject) enhancer.create();
        cglibDynamicProxyObject.action();

        // AOP 代理
        System.out.println("\nAOP 代理：");
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(targetObject);
        // 添加AOP通知
        proxyFactory.addAdvice(new MyAdviceInterceptor());
        TargetObject aopProxyObject = (TargetObject) proxyFactory.getProxy();
        aopProxyObject.action();
    }
}
