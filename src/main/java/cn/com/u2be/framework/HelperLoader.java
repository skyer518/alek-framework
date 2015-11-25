package cn.com.u2be.framework;

import cn.com.u2be.framework.helper.*;
import cn.com.u2be.framework.util.ClassUtil;

/**
 * Created by Ã÷ on 2015/11/21.
 */
public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {ClassHelper.class, BeanHelper.class, AopHelper.class, IocHelper.class, ControllerHelper.class};

        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), true);
        }
    }


}
