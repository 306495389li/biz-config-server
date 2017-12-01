package com.flaginfo.lightning;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取ApplicationContext对象
 *      运行时获取spring中的类对象
 * @author zyong
 *
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    private ApplicationContextProvider(){}

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(String name,Class<T> aClass) {
        return context.getBean(name,aClass);
    }

}
