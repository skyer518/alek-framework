package cn.com.u2be.framework.helper;

import cn.com.u2be.framework.annotation.Inject;
import cn.com.u2be.framework.util.ReflectionUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * ����ע��ʵ��
 * Created by �� on 2015/11/19.
 */
public final class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();

        if (MapUtils.isNotEmpty(beanMap)) {

            for (Map.Entry<Class<?>,Object> beanEntry :beanMap.entrySet()){
                // ȡ BEAN ���� Bean ʵ��
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)){

                    for (Field field:beanFields){
                        // �жϵ�ǰ Bean field �Ƿ���� inject ע��
                        if (field.isAnnotationPresent(Inject.class)){
                            // ȡ field ��Ӧ��ʵ��
                            Class<?> fieldClass = field.getType();
                            Object fieldInstance = beanMap.get(fieldClass);
                            if (fieldInstance!=null){
                                // ���� �ֶ�
                                ReflectionUtil.setField(beanInstance,field,fieldInstance);
                            }
                        }
                    }


                }

            }



        }


    }


}
