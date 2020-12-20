package cn.clouds.context;

import ch.qos.logback.classic.Level;
import cn.clouds.enums.ResponseEnums;
import cn.clouds.exception.Base5xxException;
import cn.clouds.utils.LogUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;

/**
 * @author clouds
 * @version 1.0
 */
public class SpringContextHolder implements DisposableBean, ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    private static void checkApplicationContext(ApplicationContext context) {
        if (context == null) {
            LogUtil.commonLog(Level.ERROR, "applicationContext is null");
            throw new Base5xxException(HttpStatus.EXPECTATION_FAILED, ResponseEnums.APPLICATION_CONTEXT_NULL, null, null);
        }
    }

    public static ApplicationContext getApplicationContext() {
        checkApplicationContext(applicationContext);
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext(applicationContext);
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> type) {
        checkApplicationContext(applicationContext);
        return (T) applicationContext.getBean(type);
    }

    public static <T> T getBean(String name, Class<T> type) {
        checkApplicationContext(applicationContext);
        return (T) applicationContext.getBean(name, type);
    }

    @Override
    public void destroy() throws Exception {
        SpringContextHolder.clear();
    }

    private static void clear() {
        applicationContext = null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }
}
