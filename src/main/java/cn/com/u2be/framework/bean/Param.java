package cn.com.u2be.framework.bean;

import cn.com.u2be.framework.util.CastUtil;
import cn.com.u2be.framework.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数
 * Created by 明 on 2015/11/21.
 */
public class Param {

    private List<FormParam> formParamList;

    private List<FileParam> fileParamList;

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    /**
     * 获取请求参数 映射
     *
     * @return
     */
    public Map<String, Object> getFieldMap() {

        Map<String, Object> fieldMap = new HashMap<String, Object>(0);
        if (MapUtils.isNotEmpty(fieldMap)) {
            for (FormParam formParam : formParamList) {
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if (fieldMap.containsKey(fieldName)) {
                    fieldValue = fieldMap.get(fieldName) + StringUtil.SEPARATOR + fieldValue;
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }
        return fieldMap;
    }

    /**
     * 获取 上传文件 映射
     *
     * @return
     */
    public Map<String, List<FileParam>> getFileMap() {

        Map<String, List<FileParam>> fileMap = new HashMap<String, List<FileParam>>(0);
        if (MapUtils.isNotEmpty(fileMap)) {
            for (FileParam fileParam : fileParamList) {
                String fieldName = fileParam.getFieldName();
                List<FileParam> fileParams;
                if (fileMap.containsKey(fieldName)) {
                    fileParams = fileMap.get(fieldName);
                } else {
                    fileParams = new ArrayList<FileParam>(0);
                }
                fileParams.add(fileParam);
                fileMap.put(fieldName, fileParams);
            }

        }
        return fileMap;
    }

    /**
     * 获取所有上传文件
     *
     * @param fieldName
     * @return
     */
    public List<FileParam> getFileList(String fieldName) {
        return getFileMap().get(fieldName);
    }

    /**
     * 获取唯一文件
     *
     * @param fieldName
     * @return
     */
    public FileParam getFile(String fieldName) {
        List<FileParam> fileParamList = getFileList(fieldName);
        if (CollectionUtils.isNotEmpty(fileParamList) && fileParamList.size() == 1) {
            return fileParamList.get(0);
        }
        return null;
    }

    /**
     * 根据参数名获取 long 型参数
     *
     * @param name
     * @return
     */
    public long getLong(String name) {
        return CastUtil.castLong(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 String 型参数
     *
     * @param name
     * @return
     */
    public String getString(String name) {
        return CastUtil.castString(getFieldMap().get(name));
    }


    /**
     * 根据参数名获取 int 型参数
     *
     * @param name
     * @return
     */
    public int getInt(String name) {
        return CastUtil.castInt(getFieldMap().get(name));
    }


    /**
     * 根据参数名获取 Boolean 型参数
     *
     * @param name
     * @return
     */
    public boolean getBoolean(String name) {
        return CastUtil.castBoolean(getFieldMap().get(name));
    }


    /**
     * 根据参数名获取 double 型参数
     *
     * @param name
     * @return
     */
    public double getDouble(String name) {
        return CastUtil.castDouble(getFieldMap().get(name));
    }

    /**
     * 检查参数是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return CollectionUtils.isNotEmpty(formParamList) && CollectionUtils.isNotEmpty(fileParamList);
    }


}
