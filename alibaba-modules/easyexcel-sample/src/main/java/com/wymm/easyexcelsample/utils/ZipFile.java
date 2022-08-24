package com.wymm.easyexcelsample.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ZipUtil;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出临时目录中的文件
 */
public class ZipFile implements AutoCloseable {
    
    /**
     * zip 父目录
     */
    private final String tempPath;
    /**
     * zip 文件
     */
    private File zipFile;
    /**
     * 要压缩的文件
     */
    private final List<File> files;
    
    public ZipFile() {
        this.tempPath = getTmpdir();
        this.files = new ArrayList<>();
    }
    
    protected String getTmpdir() {
        return System.getProperty("java.io.tmpdir");
    }
    
    /**
     * 生成压缩文件
     */
    private void zipDict() {
        if (this.zipFile == null) {
            String zipFilePath = tempPath + File.separator + RandomUtil.randomString(30) + ".zip";
            this.zipFile = new File(zipFilePath);
            ZipUtil.zip(this.zipFile, true, this.files.toArray(new File[0]));
        }
    }
    
    /**
     * 添加待压缩的文件
     *
     * @param file file
     */
    public void addFile(String file) {
        this.addFile(new File(file));
    }
    
    /**
     * 添加待压缩的文件
     *
     * @param file file
     */
    public void addFile(File file) {
        this.files.add(file);
    }
    
  
    
    /**
     * 生成压缩文件并导出
     *
     * @param response response
     * @param zipName  压缩文件名称
     * @throws IOException IOException
     */
    public void export(HttpServletResponse response, String zipName) throws IOException {
        this.zipDict();
        FileDownloadUtils.exportFile(response, zipName, zipFile);
    }
    
    /**
     * 删除压缩文件
     */
    private void del() {
        FileUtil.del(zipFile);
    }
    
    @Override
    public void close() {
        del();
    }
}
