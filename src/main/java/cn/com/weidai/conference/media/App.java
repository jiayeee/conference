package cn.com.weidai.conference.media;

import cn.com.weidai.conference.media.exception.NoLoginException;
import cn.com.weidai.conference.media.exception.UnknownException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lwz on 2018/01/14 20:02.
 */
public class App {

    @Getter
    @Setter
    class MethodBean {
        private Method  method;
        private Object  bean;
        private boolean ws;
        private boolean needsLogin;
        private Class   parameterType;

        public MethodBean(Method method, Object bean, boolean ws, boolean needsLogin, Class parameterType) {
            this.method = method;
            this.bean = bean;
            this.ws = ws;
            this.needsLogin = needsLogin;
            this.parameterType = parameterType;
        }
    }

    private App() {
    }

    private Map<Integer, MethodBean> cache = new HashMap<>();

    public static App getInstance() {
        return AppHolder.app;
    }

    private static class AppHolder {
        private static App app = new App();
    }

    public void init(SpringContextHolder springContextHolder) {
        ApplicationContext ctx = springContextHolder.getCtx();

        String[] names = ctx.getBeanDefinitionNames();
        for (String name : names) {
            Object bean = ctx.getBean(name);
            for (Method method : bean.getClass().getMethods()) {
                if (method.isAnnotationPresent(Remote.class)) {
                    Remote anno = method.getAnnotation(Remote.class);

                    if (cache.get(anno.cmd()) != null) {
                        throw new RuntimeException("重复的CMD定义:" + anno.cmd());
                    }
                    cache.put(anno.cmd(), new MethodBean(method, bean, anno.ws(), anno.needsLogin(), method.getParameterTypes()[0]));
                }
            }
        }
    }

    public Object execute(String p) throws InvocationTargetException, IllegalAccessException {
        JSONObject jsonObject = JSONObject.parseObject(p);
        Integer cmd = jsonObject.getInteger("cmd");
        if (cmd == null) {
            throw new UnknownException("缺少命令号参数");
        }

        MethodBean methodBean = cache.get(cmd);
        if (methodBean == null) {
            throw new UnknownException("无法识别的命令号: " + cmd);
        }

        if (!methodBean.ws && methodBean.needsLogin) {
            Long uid = jsonObject.getLong("uid");
            String token = jsonObject.getString("token");
            if (uid == null || StringUtils.isBlank(token)) {
                throw new NoLoginException();
            }
            authCheck(uid, token);
        }

        Method method = methodBean.getMethod();
        Object o = JSON.parseObject(p, methodBean.getParameterType());
        return method.invoke(methodBean.getBean(), o);
    }

    // TODO 
    private void authCheck(long uid, String token) {

    }
}