package com.leoman.common.holder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by yaming_deng on 14-9-11.
 */
@Service
public class AppContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static <T> T get(String beanName, Class<T> clazz) {
        return clazz.cast(getBean(beanName));
    }

    public static  <T> T get(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        AppContextHolder.applicationContext = applicationContext;
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return applicationContext.getBeansOfType(type);
    }

}
