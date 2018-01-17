package cn.com.weidai.conference.media;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by lwz on 2018/01/15 09:55.
 */
@Component
public class SpringContextHolder implements ApplicationContextAware, InitializingBean {

    private ApplicationContext ctx;

    public ApplicationContext getCtx() {
        return ctx;
    }

    public <T> T getBean(Class<T> clazz) {
        return ctx.getBean(clazz);
    }

    public Object getBean(String name) {
        return ctx.getBean(name);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        App.getInstance().init(this);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
