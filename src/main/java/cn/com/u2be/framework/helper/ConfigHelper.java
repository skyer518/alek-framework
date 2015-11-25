package cn.com.u2be.framework.helper;

import cn.com.u2be.framework.ConfigConstant;
import cn.com.u2be.framework.util.PropsUtil;

import java.util.Properties;

/**
 * �����ļ�����
 * Created by �� on 2015/11/19.
 */
public final class ConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * jdbc Driver
     *
     * @return
     */
    public static final String getJdbcDriver() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    /**
     * jdbc Url
     *
     * @return
     */
    public static final String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    /**
     * jdbc Username
     *
     * @return
     */
    public static final String getJdbcUsername() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    /**
     * jdbc Password
     *
     * @return
     */
    public static final String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * Ӧ�õĻ�������
     *
     * @return
     */
    public static final String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * jsp·��
     *
     * @return
     */
    public static final String getAppJspPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view/");
    }

    /**
     * ��̬��Դ·��
     *
     * @return
     */
    public static final String getAppAssetPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PAHT, "/asset/");
    }

    /**
     * Ӧ���ļ��ϴ�����
     *
     * @return
     */
    public static int getAppUploadLimit() {
        return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.APP_UPLOAD_LIMIT, 10);
    }
}
