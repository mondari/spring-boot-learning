package com.mondari.proxy.statics;

import com.mondari.proxy.TargetInterface;

/**
 * 静态代理类
 */
public class ProxyObject implements TargetInterface {

    /**
     * 持有目标对象的引用
     */
    TargetInterface target;

    /**
     * 通过构造函数注入目标对象
     *
     * @param target
     */
    public ProxyObject(TargetInterface target) {
        this.target = target;
    }

    @Override
    public void action() {
        System.out.println("——调用前处理——");
        // 执行目标对象动作
        target.action();
        System.out.println("——调用后处理——");
    }
}
