package cn.com.u2be.framework.helper;

import cn.com.u2be.framework.bean.FileParam;
import cn.com.u2be.framework.bean.FormParam;
import cn.com.u2be.framework.bean.Param;
import cn.com.u2be.framework.util.FileUtil;
import cn.com.u2be.framework.util.StreamUtil;
import cn.com.u2be.framework.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 明 on 2015/11/24.
 */
public final class UploadHelper {

    public static final Logger LOGGER = LoggerFactory.getLogger(UploadHelper.class);
    /**
     * Apache Commons FileUpload 提供的 Servlet 文件上传对象
     */
    private static ServletFileUpload servletFileUpload;


    public static void init(ServletContext servletContext) {
        //临时工作目录 ; 每一个servlet上下文都需要一个临时存储目录
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");

        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        int uploadLimit = ConfigHelper.getAppUploadLimit();
        if (uploadLimit != 0) {
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }

    /**
     * 是否为 multipart 类型
     *
     * @param request
     * @return
     */
    public static boolean isMultipart(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }

    /**
     * 创建请求对象
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Param createParam(HttpServletRequest request) throws IOException {

        List<FormParam> formParamList = new ArrayList<FormParam>(0);
        List<FileParam> fileParamList = new ArrayList<FileParam>(0);

        try {

            Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
            if (MapUtils.isNotEmpty(fileItemListMap)) {

                for (Map.Entry<String, List<FileItem>> fileItemEntry : fileItemListMap.entrySet()) {
                    String fieldName = fileItemEntry.getKey();
                    List<FileItem> fileItems = fileItemEntry.getValue();

                    if (CollectionUtils.isNotEmpty(fileItems)) {

                        for (FileItem fileItem : fileItems) {
                            if (fileItem.isFormField()) {
                                String fieldValue = fileItem.getString("UTF-8");
                                formParamList.add(new FormParam(fieldName, fieldValue));
                            } else {
                                String realFileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), "UTF-8"));
                                if (StringUtil.isNotEmpty(realFileName)) {

                                    long size = fileItem.getSize();
                                    String contentType = fileItem.getContentType();
                                    InputStream inputStream = fileItem.getInputStream();
                                    fileParamList.add(new FileParam(realFileName, fieldName, size, contentType, inputStream));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("create param failure ", e);
            throw new RuntimeException(e);
        }
        return new Param(formParamList, fileParamList);

    }

    /**
     * 上传文件
     *
     * @param basePath
     * @param fileParam
     */
    public static void uploadFile(String basePath, FileParam fileParam) {

        try {
            String filePath = basePath + fileParam.getFileName();
            FileUtil.createFile(filePath);
            InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
            StreamUtil.copyStream(inputStream, outputStream);
        } catch (Exception e) {
            LOGGER.error("upload file failure", e);
            throw new RuntimeException(e);
        }

    }

    /**
     * 批量上传文件
     *
     * @param basePath
     * @param fileParamList
     */
    public static void uploadFile(String basePath, List<FileParam> fileParamList) {
        try {
            if (CollectionUtils.isNotEmpty(fileParamList)) {
                for (FileParam fileParam : fileParamList) {
                    uploadFile(basePath, fileParam);
                }
            }
        } catch (Exception e) {
            LOGGER.error("upload file failure", e);
            throw new RuntimeException(e);
        }
    }


}
