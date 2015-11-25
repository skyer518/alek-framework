package cn.com.u2be.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回的视图对象
 * <p>
 * Created by 明 on 2015/11/21.
 */
public class View {
    /**
     * 视图路径
     */
    private String path;

    /**
     * 模型数据
     */
    private Map<String, Object> model;


    public View(String path) {
        this.path = path;
        this.model = new HashMap<String, Object>(0);

    }


    public View addModel(String key, Object value) {
        model.put(key, value);
        return this;
    }


    public String getPath() {
        return this.path;
    }

    public Map<String, Object> getModel() {
        return this.model;
    }
}
