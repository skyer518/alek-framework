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
 * ������ ������
 * Created by �� on 2015/11/19.
 */
public class ControllerHelper {
    /**
     * ���ڴ������ ��  ������ ��ӳ���ϵ
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>(0);


    static {

        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();

        if (CollectionUtils.isNotEmpty(controllerClassSet)) {

            // ���� ��Щ Controller
            for (Class<?> controllerClass : controllerClassSet) {

                //��ȡController �ж���� ����

                Method[] declaredMethods = controllerClass.getDeclaredMethods();

                if (ArrayUtils.isNotEmpty(declaredMethods)) {
                    // ������Щ����
                    for (Method method : declaredMethods) {
                        // �ж���Щ�����Ƿ���� Action ע��

                        if (method.isAnnotationPresent(Action.class)) {

                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            // ��֤urlӳ�����
                            if (mapping.matches("\\w+:/\\w*")) {

                                String[] array = mapping.split(":");
                                if (ArrayUtils.isNotEmpty(array) && array.length == 2) {

                                    // ��ȡ���󷽷�������·��

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
     * ��ȡ handler
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
