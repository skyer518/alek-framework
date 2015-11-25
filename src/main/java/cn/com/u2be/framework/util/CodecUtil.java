package cn.com.u2be.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * ���� / ���� ����
 * Created by �� on 2015/11/21.
 */
public class CodecUtil {


    public static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);

    /**
     * �� URL ����
     *
     * @param source
     * @return
     */
    public static String encodeURL(String source) {
        String result;
        try {
            result = URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("encode url failure", e);
            throw new RuntimeException(e);
        }
        return result;

    }

    /**
     * �� URL ����
     *
     * @param source
     * @return
     */
    public static String decodeURL(String source) {
        String result;
        try {
            result = URLDecoder.decode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("decode url failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }


}
