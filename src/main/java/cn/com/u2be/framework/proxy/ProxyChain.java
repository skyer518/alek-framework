package cn.com.u2be.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ã÷ on 2015/11/23.
 */
public class ProxyChain {

    private final Class<?> targetClass;

    private final Object targetBean;

    private final Method targetMethod;

    private final MethodProxy methodProxy;

    private final Object[] methodParams;


    private List<Proxy> proxyList = new ArrayList<Proxy>(0);

    private int proxyIndex = 0;


    public ProxyChain(Class<?> targetClass, Object targetBean, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetBean = targetBean;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }


    public Class<?> getTargetClass() {
        return this.targetClass;
    }

    public Method getTargetMethod() {
        return this.targetMethod;
    }

    public Object[] getMethodParams() {
        return this.methodParams;
    }


    public Object doProxyChain() throws Throwable {
        Object methodResult;
        if (proxyIndex < proxyList.size()) {
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            methodResult = methodProxy.invokeSuper(targetBean, methodParams);
        }

        return methodResult;


    }


}
