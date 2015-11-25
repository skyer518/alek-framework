package cn.com.u2be.framework.helper;

import cn.com.u2be.framework.bean.FormParam;
import cn.com.u2be.framework.bean.Param;
import cn.com.u2be.framework.util.CodecUtil;
import cn.com.u2be.framework.util.StreamUtil;
import cn.com.u2be.framework.util.StringUtil;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by 明 on 2015/11/24.
 */
public final class RequestHelper {

    /**
     * 创建请求参数对象
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Param createParam(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<FormParam>(0);
        formParamList.addAll(parseParameterNames(request));
        formParamList.addAll(parseInputStream(request));
        return new Param(formParamList);
    }

    private static List<FormParam> parseParameterNames(HttpServletRequest request) {
        List<FormParam> formParamList = new ArrayList<FormParam>(0);
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String fieldName = parameterNames.nextElement();
            String[] parameterValues = request.getParameterValues(fieldName);
            if (ArrayUtils.isNotEmpty(parameterValues)) {
                Object fieldValue;
                if (parameterValues.length == 1) {
                    fieldValue = parameterValues[0];
                } else {
                    StringBuilder sb = new StringBuilder("");
                    for (int i = 0; i < parameterValues.length; i++) {
                        sb.append(parameterValues[i]);
                        if (i != parameterValues.length - 1) {
                            sb.append(StringUtil.SEPARATOR);
                        }
                    }
                    fieldValue = sb.toString();
                }
                formParamList.add(new FormParam(fieldName, fieldValue));
            }
        }
        return formParamList;
    }

    private static List<FormParam> parseInputStream(HttpServletRequest request) throws IOException {

        List<FormParam> formParamList = new ArrayList<FormParam>(0);
        String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));

        if (StringUtil.isNotEmpty(body)) {
            String[] kvs = body.split("&");
            if (ArrayUtils.isNotEmpty(kvs)) {
                for (String kv : kvs) {
                    String[] array = kv.split("=");
                    if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                        String fieldName = array[0];
                        String fieldValue = array[1];
                        formParamList.add(new FormParam(fieldName, fieldValue));
                    }
                }
            }
        }
        return formParamList;
    }


}
