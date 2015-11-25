package cn.com.u2be.framework.helper;

import cn.com.u2be.framework.annotation.Bean;
import cn.com.u2be.framework.annotation.Controller;
import cn.com.u2be.framework.annotation.Service;
import cn.com.u2be.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手
 * Created by 明 on 2015/11/19.
 */
public final class ClassHelper {
    /**
     * 定义类的集合
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包名上的所有类
     *
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation() {
        return CLASS_SET;
    }

    /**
     * 获取应用包名下的所有 Service 类
     *
     * @return
     */
    public static Set<Class<?>> getServiceClassSet() {
        return getClassSetByAnnotation(Service.class);
    }
    /**
     * 获取应用包名下的所有 Bean 类
     *
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        return getClassSetByAnnotation(Bean.class);
    }

    /**
     * 获取应用包名下的所有 Controller 类
     *
     * @return
     */
    public static Set<Class<?>> getControllerClassSet() {
        return getClassSetByAnnotation(Controller.class);
    }

    /**
     * 获取应用包名下的所有 Bean 类 （Service,Controller,Bean）
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
     * 获取 应用包名 下带有某类注解类的 所有类
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
     * 获取 应用包名 下 《实现某接口》或《继承某父类》的 所有子类(实现类)
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
