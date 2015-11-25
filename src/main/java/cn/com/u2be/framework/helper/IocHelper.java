package cn.com.u2be.framework.helper;

import cn.com.u2be.framework.annotation.Inject;
import cn.com.u2be.framework.util.ReflectionUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入实例
 * Created by 明 on 2015/11/19.
 */
public final class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();

        if (MapUtils.isNotEmpty(beanMap)) {

            for (Map.Entry<Class<?>,Object> beanEntry :beanMap.entrySet()){
                // 取 BEAN 类与 Bean 实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)){

                    for (Field field:beanFields){
                        // 判断当前 Bean field 是否带有 inject 注解
                        if (field.isAnnotationPresent(Inject.class)){
                            // 取 field 对应的实例
                            Class<?> fieldClass = field.getType();
                            Object fieldInstance = beanMap.get(fieldClass);
                            if (fieldInstance!=null){
                                // 设置 字段
                                ReflectionUtil.setField(beanInstance,field,fieldInstance);
                            }
                        }
                    }


                }

            }



        }


    }


}
