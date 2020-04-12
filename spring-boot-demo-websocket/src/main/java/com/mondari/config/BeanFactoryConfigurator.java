package com.mondari.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpointConfig;

/**
 * <p>
 * 自定义Configurator
 * </p>
 *
 * @author limondar
 * @date 2020/4/7
 */
@Component
public class BeanFactoryConfigurator extends ServerEndpointConfig.Configurator implements BeanFactoryAware {
    private static BeanFactory context;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        context = beanFactory;
    }

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        return context.getBean(clazz);
    }
}
