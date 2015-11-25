package cn.com.u2be.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * ���ص���ͼ����
 * <p>
 * Created by �� on 2015/11/21.
 */
public class View {
    /**
     * ��ͼ·��
     */
    private String path;

    /**
     * ģ������
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
