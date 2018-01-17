package cn.com.weidai.conference.media;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Remote {

    public int cmd() default 0;

    // 是否websocket响应，是的话不检验needsLogin
    public boolean ws() default false;

    public boolean needsLogin() default true;
}