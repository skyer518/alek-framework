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
 * �������
 * Created by �� on 2015/11/21.
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
     * ��ȡ������� ӳ��
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
     * ��ȡ �ϴ��ļ� ӳ��
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
     * ��ȡ�����ϴ��ļ�
     *
     * @param fieldName
     * @return
     */
    public List<FileParam> getFileList(String fieldName) {
        return getFileMap().get(fieldName);
    }

    /**
     * ��ȡΨһ�ļ�
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
     * ���ݲ�������ȡ long �Ͳ���
     *
     * @param name
     * @return
     */
    public long getLong(String name) {
        return CastUtil.castLong(getFieldMap().get(name));
    }

    /**
     * ���ݲ�������ȡ String �Ͳ���
     *
     * @param name
     * @return
     */
    public String getString(String name) {
        return CastUtil.castString(getFieldMap().get(name));
    }


    /**
     * ���ݲ�������ȡ int �Ͳ���
     *
     * @param name
     * @return
     */
    public int getInt(String name) {
        return CastUtil.castInt(getFieldMap().get(name));
    }


    /**
     * ���ݲ�������ȡ Boolean �Ͳ���
     *
     * @param name
     * @return
     */
    public boolean getBoolean(String name) {
        return CastUtil.castBoolean(getFieldMap().get(name));
    }


    /**
     * ���ݲ�������ȡ double �Ͳ���
     *
     * @param name
     * @return
     */
    public double getDouble(String name) {
        return CastUtil.castDouble(getFieldMap().get(name));
    }

    /**
     * �������Ƿ�Ϊ��
     *
     * @return
     */
    public boolean isEmpty() {
        return CollectionUtils.isNotEmpty(formParamList) && CollectionUtils.isNotEmpty(fileParamList);
    }


}
