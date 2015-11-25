package cn.com.u2be.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Ã÷ on 2015/11/25.
 */
public final class ServletHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServletHelper.class);


    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_THREAD_LOCAL = new ThreadLocal<ServletHelper>();


    private HttpServletRequest request;

    private HttpServletResponse response;

    private ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * ³õÊ¼»¯
     *
     * @param request
     * @param response
     */
    public static void init(HttpServletRequest request, HttpServletResponse response) {
        SERVLET_HELPER_THREAD_LOCAL.set(new ServletHelper(request, response));
    }


    public static void destroy() {
        SERVLET_HELPER_THREAD_LOCAL.remove();
    }

    public static HttpServletRequest getRequest() {
        return SERVLET_HELPER_THREAD_LOCAL.get().request;
    }

    public static HttpServletResponse getResponse() {
        return SERVLET_HELPER_THREAD_LOCAL.get().response;
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }

    public static void setRequestAttribute(String key, Object value) {
        getRequest().setAttribute(key, value);
    }

    public static <T> T getRequestAttribute(String key) {
        return (T) getRequest().getAttribute(key);
    }

    public static void removeRequestAttribute(String key) {
        getRequest().removeAttribute(key);
    }


    public static void setSessionAttribute(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static <T> T getSessionAttribute(String key) {
        return (T) getSession().getAttribute(key);
    }

    public static void removeSessionAttribute(String key) {
        getSession().removeAttribute(key);
    }

    public static void invalidateSession() {
        getSession().invalidate();
    }


    public static void sendRedirect(String location) {
        try {
            getResponse().sendRedirect(getRequest().getContextPath() + location);
        } catch (IOException e) {
            LOGGER.error("redirect failure", e);
        }
    }


}
