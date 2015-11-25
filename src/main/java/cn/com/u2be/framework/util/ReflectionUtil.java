package cn.com.u2be.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * ���� ������
 * Created by �� on 2015/11/19.
 */
public final class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * ����ʵ��
     * @param cls
     * @return
     */
    public static Object newInstance(Class<?> cls) {

        try {
            return cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("new instance failure", e);
            throw new RuntimeException(e);
        }

    }

    /**
     * ���÷���
     * @param obj
     * @param method
     * @param args
     * @return
     */
    public static Object invokeMethod(Object obj,Method method,Object... args){
        try {
            method.setAccessible(true);
            return method.invoke(obj, args);
        } catch (Exception e) {
            LOGGER.error("Invoke Method failure",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * ���� �ֶ�
     * @param obj
     * @param field
     * @param value
     */
    public static void setField(Object obj,Field field,Object value){
        try {
            field.setAccessible(true);
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            LOGGER.error("Set Field failure",e);
            throw new RuntimeException(e);
        }
    }


}
