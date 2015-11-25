package cn.com.u2be.framework.helper;

import cn.com.u2be.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 明 on 2015/11/19.
 */
public final class BeanHelper {

    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>(0);

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBenClassSet();
        for (Class<?> beanClass : beanClassSet) {
            Object instance = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, instance);
        }
    }

    /**
     * get all bean实例
     * @return
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * get Bean 实例
     * @param cls
     */
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not get bean by class:" + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }

    /**
     * set Bean 实例
     * @param cls
     * @param ojb
     */
    public static void setBean(Class<?> cls, Object ojb) {
        BEAN_MAP.put(cls, ojb);
    }


}
