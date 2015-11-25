package cn.com.u2be.framework;

import cn.com.u2be.framework.bean.Data;
import cn.com.u2be.framework.bean.Handler;
import cn.com.u2be.framework.bean.Param;
import cn.com.u2be.framework.bean.View;
import cn.com.u2be.framework.helper.*;
import cn.com.u2be.framework.util.JsonUtil;
import cn.com.u2be.framework.util.ReflectionUtil;
import cn.com.u2be.framework.util.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by 明 on 2015/11/22.
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {


    @Override
    public void init(ServletConfig config) throws ServletException {
        // 初始化 相关的 Helper 类
        HelperLoader.init();

        ServletContext servletContext = config.getServletContext();
        // 注册处理 Jsp 的 Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        // 注册处理静态资源 的默认 Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");

        UploadHelper.init(servletContext);

    }


    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletHelper.init(request, response);
        try {
            // get 请求方法 and 请求路径
            String requestMethod = request.getMethod().toLowerCase();
            String requestPathInfo = request.getPathInfo();

            if (requestPathInfo.equals("/favicon.ico")) {
                return;
            }
            // get Action 处理器
            Handler handler = ControllerHelper.getHandler(requestMethod, requestPathInfo);

            if (handler != null) {
                // get Controller类 及其 Bean实例
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);
                //创建请求参数 对象
                Param param;
                if (UploadHelper.isMultipart(request)) {
                    param = UploadHelper.createParam(request);
                } else {
                    param = RequestHelper.createParam(request);
                }

                // invoke actionMethod
                Object result;
                Method actionMethod = handler.getActionMethod();

                if (param.isEmpty()) {   // 有些地方是可以不用 携带参数的
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
                } else {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
                }

                // handle result
                if (result instanceof View) {
                    handleViewResult(request, response, (View) result);
                } else if (result instanceof Data) {
                    handleDataResult((Data) request, response);
                }

            }
        } finally {
            ServletHelper.destroy();
        }


    }

    private void handleDataResult(Data request, HttpServletResponse response) throws IOException {
        Data data = request;
        Object model = data.getModel();
        if (model != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

    private void handleViewResult(HttpServletRequest request, HttpServletResponse response, View result) throws IOException, ServletException {
        View view = result;

        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {

            if (path.startsWith("/")) {
                response.sendRedirect(request.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
            }


        }
    }

}