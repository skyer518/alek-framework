package cn.com.u2be.framework.util;

/**
 * Created by �� on 2015/11/21.
 */
public final class CastUtil {
    /**
     * תΪString Ĭ��Ϊ����
     *
     * @param obj
     * @return
     */
    public static String castString(Object obj) {
        return castString(obj, "");
    }

    /**
     * תΪString
     *
     * @param obj
     * @return
     */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * תΪ double Ĭ��Ϊ0
     *
     * @param obj
     * @return
     */
    public static double castDouble(Object obj) {
        return castDouble(obj, 0);
    }

    /**
     * תΪ double
     *
     * @param obj
     * @return
     */
    public static double castDouble(Object obj, double defaultValue) {

        double result = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {

                try {
                    result = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    result = defaultValue;
                }
            }
        }
        return result;

    }


    /**
     * תΪ long Ĭ��Ϊ0
     *
     * @param obj
     * @return
     */
    public static long castLong(Object obj) {
        return castLong(obj, 0L);
    }

    /**
     * תΪ long
     *
     * @param obj
     * @return
     */
    public static long castLong(Object obj, long defaultValue) {

        long result = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {

                try {
                    result = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    result = defaultValue;
                }
            }
        }
        return result;
    }


    /**
     * תΪ int Ĭ��Ϊ0
     *
     * @param obj
     * @return
     */
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    /**
     * תΪ int
     *
     * @param obj
     * @return
     */
    public static int castInt(Object obj, int defaultValue) {

        int result = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {

                try {
                    result = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    result = defaultValue;
                }
            }
        }
        return result;
    }


    /**
     * תΪ boolean Ĭ��Ϊ0
     *
     * @param obj
     * @return
     */
    public static boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }

    /**
     * תΪ boolean
     *
     * @param obj
     * @return
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {

        boolean result = defaultValue;
        if (obj != null) {
            result = Boolean.parseBoolean(castString(obj));
        }
        return result;
    }


}
