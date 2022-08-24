package com.wymm.easyexcelsample.utils;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RandomUtil;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class FileDownloadUtils {
    
    /**
     * 导出文件
     *
     * @param response response
     * @param fileName 文件名
     * @param file     文件
     * @throws IOException IOException
     */
    public static void exportFile(HttpServletResponse response, String fileName, File file) throws IOException {
        String type = FileTypeUtil.getType(file);
        fileName = String.format("%s%s%s", URLEncoder.encode(fileName, "UTF-8"), ".", type);
        
        // 根据实际的文件类型找到对应的 contentType
        String contentType = MediaTypeFactory.getMediaType(fileName)
                .map(MediaType::toString)
                .orElse("application/octet-stream");
        response.setContentType(contentType);
        response.setCharacterEncoding("UTF-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
        
        try (ServletOutputStream os = response.getOutputStream();
             FileInputStream fis = new FileInputStream(file)) {
            IoUtil.copy(fis, os);
            os.flush();
        }
    }
    
    /**
     * @return 临时目录
     */
    public static String getFileTempPath() {
        return System.getProperty("java.io.tmpdir");
    }
    
    /**
     * 获取临时目录下创建一个随机名称的目录路径
     *
     * @return 随机名称目录路径
     */
    public static String getFileTempPathRandomChild() {
        return getFileTempPath() + RandomUtil.randomString(30);
    }
    
    /**
     * 临时目录下创建一个随机名称的目录
     *
     * @return 随机名称目录文件
     */
    public static File mkdirFileTempPathRandomChild() {
        String path = getFileTempPathRandomChild();
        return FileUtil.mkdir(path);
    }
    
    
}
