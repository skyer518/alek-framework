package cn.com.u2be.framework.bean;

/**
 * 返回的 数据对象
 * Created by 明 on 2015/11/21.
 */
public class Data {
    /**
     * 数据模型
     */
    private Object model;


    public Data(Object model) {
        this.model = model;
    }


    public Object getModel() {
        return this.model;
    }
}
