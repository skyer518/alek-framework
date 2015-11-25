package cn.com.u2be.framework.helper;

import cn.com.u2be.framework.annotation.Bean;
import cn.com.u2be.framework.annotation.Controller;
import cn.com.u2be.framework.annotation.Service;
import cn.com.u2be.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * ���������
 * Created by �� on 2015/11/19.
 */
public final class ClassHelper {
    /**
     * ������ļ���
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * ��ȡӦ�ð����ϵ�������
     *
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation() {
        return CLASS_SET;
    }

    /**
     * ��ȡӦ�ð����µ����� Service ��
     *
     * @return
     */
    public static Set<Class<?>> getServiceClassSet() {
        return getClassSetByAnnotation(Service.class);
    }
    /**
     * ��ȡӦ�ð����µ����� Bean ��
     *
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        return getClassSetByAnnotation(Bean.class);
    }

    /**
     * ��ȡӦ�ð����µ����� Controller ��
     *
     * @return
     */
    public static Set<Class<?>> getControllerClassSet() {
        return getClassSetByAnnotation(Controller.class);
    }

    /**
     * ��ȡӦ�ð����µ����� Bean �� ��Service,Controller,Bean��
     *
     * @return
     */
    public static Set<Class<?>> getBenClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<Class<?>>(0);
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        beanClassSet.addAll(getBeanClassSet());
        return beanClassSet;
    }

    /**
     * ��ȡ Ӧ�ð��� �´���ĳ��ע����� ������
     *
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>(0);
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(annotationClass)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }


    /**
     * ��ȡ Ӧ�ð��� �� ��ʵ��ĳ�ӿڡ��򡶼̳�ĳ���ࡷ�� ��������(ʵ����)
     *
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>(0);
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAssignableFrom(superClass) && !superClass.equals(superClass)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }


}
