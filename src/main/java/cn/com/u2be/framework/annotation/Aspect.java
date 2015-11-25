package cn.com.u2be.framework.annotation;

import java.lang.annotation.*;

/**
 * Action ����ע��
 * Created by �� on 2015/11/19.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * ע��
     *
     * @return
     */
    Class<? extends Annotation> value();
}
