package com.mondari;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * AOP原理，动态代理，拦截器
 */
public class DemoProxy {
    public static void main(String[] args) {
        People people = new People();
        MyInvocationHandler handler = new MyInvocationHandler();
        handler.setTarget(people);
        Person person = (Person) Proxy.newProxyInstance(people.getClass().getClassLoader(), people.getClass().getInterfaces(), handler);
        person.talk("what");
    }
}

class MyInvocationHandler implements InvocationHandler {

    // 需要被代理的对象
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Interceptor interceptor = new Interceptor();

        System.out.println(proxy.getClass().getName());
        Object result = method.invoke(target, args);

        interceptor.after();
        return result;
    }
}

class Interceptor {
    void before() {
        System.out.println("before");
    }

    void after() {
        System.out.println("after");
    }
}

interface Person {

    void talk(String what);

    void walk(String where);
}

class People implements Person {

    @Override
    public void talk(String what) {
        System.out.println("talk" + what);
    }

    @Override
    public void walk(String where) {
        System.out.println("go" + where);
    }
}