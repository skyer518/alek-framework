package cn.com.u2be.framework.aspect;

import cn.com.u2be.framework.annotation.Aspect;
import cn.com.u2be.framework.annotation.Controller;
import cn.com.u2be.framework.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * ���� Controller ���з���
 * Created by �� on 2015/11/23.
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    public void before(Class<?> targetClass, Method targetMethod, Object[] methodParams) throws Throwable {
        LOGGER.debug("------------< begin >------------");
        LOGGER.debug(String.format("Class : %s"), targetClass.getName());
        LOGGER.debug(String.format("Method : %s"), targetMethod.getName());
        begin = System.currentTimeMillis();

    }

    @Override
    public void after(Class<?> targetClass, Method targetMethod, Object[] methodParams, Object result) throws Throwable {
        LOGGER.debug(String.format("time : %s"), System.currentTimeMillis() - begin);
        LOGGER.debug("------------< end >------------");
    }
}
