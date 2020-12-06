package com.mondari.proxy.aop.introduction;

import com.mondari.proxy.TargetObject;
import org.springframework.aop.framework.ProxyFactory;

/**
 * <p>
 * AOP Introduction Advice 引入通知示例
 * </p>
 *
 * @author limondar
 * @date 2020/12/6
 */
public class IntroductionApplication {
    public static void main(String[] args) {
        TargetObject targetObject = new TargetObject();

        ProxyFactory proxyFactory = new ProxyFactory();
        // 添加AOP通知
        proxyFactory.addAdvice(new MyIntroductionInterceptor());
        // 设置目标类
        proxyFactory.setTarget(targetObject);
        // 创建代理类
        Introducable introduction = (Introducable) proxyFactory.getProxy();
        introduction.introduce();

    }
}
