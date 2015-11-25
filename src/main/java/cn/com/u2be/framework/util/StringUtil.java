package cn.com.u2be.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by �� on 2015/11/21.
 */
public class StringUtil {

    public static final String SEPARATOR = String.valueOf((char) 29);

    /**
     * �ж��ַ����Ƿ�Ϊ��
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * �ж��ַ����Ƿ�Ϊ��
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

}
