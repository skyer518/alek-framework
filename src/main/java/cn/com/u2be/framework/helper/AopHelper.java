package cn.com.u2be.framework.helper;

import cn.com.u2be.framework.annotation.Aspect;
import cn.com.u2be.framework.annotation.Service;
import cn.com.u2be.framework.annotation.Transaction;
import cn.com.u2be.framework.proxy.AspectProxy;
import cn.com.u2be.framework.proxy.Proxy;
import cn.com.u2be.framework.proxy.ProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * aop ������
 * Created by �� on 2015/11/23.
 */
public final class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> entry : targetMap.entrySet()) {
                Class<?> targetClass = entry.getKey();
                List<Proxy> proxyList = entry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }

        } catch (Exception e) {
            LOGGER.error("aop failure", e);
        }


    }

    /**
     * ��ȡ ���� Aspect ע���������
     *
     * @param aspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<Class<?>>(0);

        Class<? extends Annotation> annotationClass = aspect.value();
        if (annotationClass != null && !annotationClass.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotationClass));
        }
        return targetClassSet;
    }

    /**
     * ��ȡ�������ࡷ�� ������Ŀ���ࡷ֮���ӳ���ϵ
     *
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {

        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>(0);
        proxyMap.putAll(createAspectProxy());
        proxyMap.putAll(createTransactionProxy());
        return proxyMap;
       /* Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);

        for (Class<?> proxyClass : proxyClassSet) {

            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
        return proxyMap;
        */
    }

    /**
     * ��ȡ��Ŀ���ࡷ�� ����������б�֮���ӳ��
     *
     * @param proxyMap
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>(0);

        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();

            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<Proxy>(0);
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }

        }
        return targetMap;

    }

    /**
     * ��ȡ ��Ŀ���ࡷ�롶��ͨ�������ӳ���ϵ
     *
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, Set<Class<?>>> createAspectProxy() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>(0);
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
        return proxyMap;
    }

    /**
     * ��ȡ ��Ŀ���ࡷ�롶����������ӳ���ϵ
     *
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, Set<Class<?>>> createTransactionProxy() throws Exception {

        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>(0);
        Set<Class<?>> serviceClass = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(Transaction.class, serviceClass);
        return proxyMap;
    }


}
