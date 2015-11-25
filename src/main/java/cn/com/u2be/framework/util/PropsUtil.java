package cn.com.u2be.framework.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties ���ع�����
 * Created by �� on 2015/11/19.
 */
public class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * load Properties
     *
     * @param fileName
     * @return
     */
    public static Properties loadProps(String fileName) {
        Properties properties = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + " file is not found");
            }
            properties = new Properties();

            properties.load(is);
        } catch (IOException e) {
            LOGGER.error("load Properties file failure", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close inputStream failure", e);
                }
            }
        }
        return properties;
    }

    /**
     * ��ȡ�ַ��������ԣ� default is  ""��
     *
     * @param props
     * @param key
     * @return
     */
    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }

    /**
     * ��ȡ�ַ���������
     *
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Properties props, String key, String defaultValue) {
        String result = defaultValue;
        if (props.contains(key)) {
            result = props.getProperty(key);
        }
        return result;
    }


    /**
     * ��ȡ int �����ԣ� default is  0��
     *
     * @param props
     * @param key
     * @return
     */
    public static int getInt(Properties props, String key) {
        return getInt(props, key, 0);
    }

    /**
     * ��ȡ int ������
     *
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(Properties props, String key, int defaultValue) {
        int result = defaultValue;
        if (props.contains(key)) {
            result = CastUtil.castInt(props.getProperty(key));
        }
        return result;
    }


    /**
     * ��ȡ int �����ԣ� default is  false��
     *
     * @param props
     * @param key
     * @return
     */
    public static boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }

    /**
     * ��ȡ int ������
     *
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        boolean result = defaultValue;
        if (props.contains(key)) {
            result = CastUtil.castBoolean(props.getProperty(key));
        }
        return result;
    }


}
