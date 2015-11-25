package cn.com.u2be.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by 明 on 2015/11/21.
 */
public final class JsonUtil {


    public static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);


    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将POJO转为 JSON
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJson(T obj) {
        String json;

        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("convert POJO to JSON failure", e);
            throw new RuntimeException(e);
        }

        return json;
    }

    /**
     * 将 JSON 转为  POJO
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> cls) {

        T pojo;

        try {
            pojo = OBJECT_MAPPER.readValue(json, cls);
        } catch (IOException e) {
            LOGGER.error("convert JSON to POJO failure", e);
            throw new RuntimeException(e);
        }

        return pojo;

    }


}
