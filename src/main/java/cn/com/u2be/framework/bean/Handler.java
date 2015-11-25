package cn.com.u2be.framework.bean;

import java.lang.reflect.Method;

/**
 *
 * Created by �� on 2015/11/19.
 */
public class Handler {
    /**
     * action ����
     */
    private Method actionMethod;
    /**
     * ������ ��
     */
    private Class<?> controllerClass;


    public Method getActionMethod() {
        return this.actionMethod;
    }


    public Class<?> getControllerClass() {
        return this.controllerClass;
    }

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.actionMethod = actionMethod;
        this.controllerClass = controllerClass;
    }
}
