package cn.com.u2be.framework.annotation;

import java.lang.annotation.*;

/**
 * Action 方法注解
 * Created by 明 on 2015/11/19.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 注解
     *
     * @return
     */
    Class<? extends Annotation> value();
}
