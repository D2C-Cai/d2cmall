package com.d2c.common.core.helper;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Map.Entry;

@Lazy(false)
@Component
public class SpringHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext app) {
        applicationContext = app;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clz) {
        return applicationContext.getBean(clz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(T bean) {
        if (bean == null) return null;
        return (T) getBean(bean.getClass());
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return applicationContext.getBeansOfType(type);
    }

    /**
     * 向spring容器注册bean
     */
    public static <T> void registerBean(String beanName, Class<T> clz, Map<String, Object> propertyMap) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) context.getBeanFactory();
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clz);
        if (propertyMap != null) {
            for (Entry<String, Object> entry : propertyMap.entrySet()) {
                builder.addPropertyValue(entry.getKey(), entry.getValue());
            }
        }
        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
    }

}
