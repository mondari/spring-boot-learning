package com.mondari.proxy.aop.introduction;

import org.springframework.aop.support.DelegatingIntroductionInterceptor;

/**
 * <p>
 * 自定义引入通知拦截器（继承 DelegatingIntroductionInterceptor，实现需要引入接口的方法）
 * </p>
 *
 * @author limondar
 * @date 2020/12/6
 */
public class MyIntroductionInterceptor extends DelegatingIntroductionInterceptor implements Introducable {

    @Override
    public void introduce() {
        System.out.println("AOP Intruduction Advice 引入通知给目标类引入了新的方法");
    }
}
