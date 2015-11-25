package cn.com.u2be.framework.helper;

import cn.com.u2be.framework.annotation.Action;
import cn.com.u2be.framework.bean.Handler;
import cn.com.u2be.framework.bean.Request;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器 助手类
 * Created by 明 on 2015/11/19.
 */
public class ControllerHelper {
    /**
     * 用于存放请求 与  处理器 的映射关系
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>(0);


    static {

        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();

        if (CollectionUtils.isNotEmpty(controllerClassSet)) {

            // 遍历 这些 Controller
            for (Class<?> controllerClass : controllerClassSet) {

                //获取Controller 中定义的 方法

                Method[] declaredMethods = controllerClass.getDeclaredMethods();

                if (ArrayUtils.isNotEmpty(declaredMethods)) {
                    // 遍历这些方法
                    for (Method method : declaredMethods) {
                        // 判断这些方法是否带有 Action 注解

                        if (method.isAnnotationPresent(Action.class)) {

                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            // 验证url映射规则
                            if (mapping.matches("\\w+:/\\w*")) {

                                String[] array = mapping.split(":");
                                if (ArrayUtils.isNotEmpty(array) && array.length == 2) {

                                    // 获取请求方法与请求路径

                                    String requestMethod = array[0];
                                    String requestPath = array[1];


                                    Request request = new Request(requestMethod, requestPath);

                                    Handler handler = new Handler(controllerClass, method);

                                    ACTION_MAP.put(request, handler);


                                }
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 获取 handler
     *
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }


}
