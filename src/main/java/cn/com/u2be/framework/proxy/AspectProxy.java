package cn.com.u2be.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理
 * Created by 明 on 2015/11/23.
 */
public abstract class AspectProxy implements Proxy {


    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);


    public Object doProxy(ProxyChain proxyChain) throws Throwable {

        Object result = null;

        final Class<?> targetClass = proxyChain.getTargetClass();
        final Method targetMethod = proxyChain.getTargetMethod();
        final Object[] methodParams = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(targetClass, targetMethod, methodParams)) {
                before(targetClass, targetMethod, methodParams);
                result = proxyChain.doProxyChain();
                after(targetClass, targetMethod, methodParams, result);
            } else {
                result = proxyChain.doProxyChain();
            }

        } catch (Exception e) {
            LOGGER.error("proxy failure", e);
            error(targetClass, targetMethod, methodParams, e);
            throw e;
        } finally {
            end();
        }


        return result;
    }

    public boolean intercept(Class<?> targetClass, Method targetMethod, Object[] methodParams) throws Throwable {
        return true;
    }


    public void begin() {
    }


    public void before(Class<?> targetClass, Method targetMethod, Object[] methodParams) throws Throwable {
    }


    public void after(Class<?> targetClass, Method targetMethod, Object[] methodParams, Object result) throws Throwable {
    }


    public void error(Class<?> targetClass, Method targetMethod, Object[] methodParams, Throwable e) {
    }


    public void end() {
    }


}
