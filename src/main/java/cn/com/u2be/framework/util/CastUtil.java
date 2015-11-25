package cn.com.u2be.framework.util;

/**
 * Created by 明 on 2015/11/21.
 */
public final class CastUtil {
    /**
     * 转为String 默认为“”
     *
     * @param obj
     * @return
     */
    public static String castString(Object obj) {
        return castString(obj, "");
    }

    /**
     * 转为String
     *
     * @param obj
     * @return
     */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转为 double 默认为0
     *
     * @param obj
     * @return
     */
    public static double castDouble(Object obj) {
        return castDouble(obj, 0);
    }

    /**
     * 转为 double
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
     * 转为 long 默认为0
     *
     * @param obj
     * @return
     */
    public static long castLong(Object obj) {
        return castLong(obj, 0L);
    }

    /**
     * 转为 long
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
     * 转为 int 默认为0
     *
     * @param obj
     * @return
     */
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    /**
     * 转为 int
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
     * 转为 boolean 默认为0
     *
     * @param obj
     * @return
     */
    public static boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }

    /**
     * 转为 boolean
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
