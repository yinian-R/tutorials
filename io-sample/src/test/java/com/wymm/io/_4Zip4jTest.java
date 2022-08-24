package com.wymm.io;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 我们将学习如何创建受密码保护的 zip 文件并使用 Zip4j 库解压缩它们。它是最全面的 zip 文件 Java 库
 */
public class _4Zip4jTest {
    
    /**
     * 压缩文件
     */
    @Test
    void zipOneFile() throws ZipException {
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setEncryptFiles(true);
        zipParameters.setCompressionLevel(CompressionLevel.HIGHER);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);
        
        ZipFile zipFile = new ZipFile(TestFileUtil.getTargetPath() + "zip/zip4j-compressed.zip", "password".toCharArray());
        zipFile.addFile(new File(TestFileUtil.getTestResourcePath() + "zip/test1.txt"), zipParameters);
    }
    
    /**
     * 压缩多个文件
     */
    @Test
    void zipManyFile() throws ZipException {
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setEncryptFiles(true);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);
        
        List<File> filesToAdd = Arrays.asList(
                new File(TestFileUtil.getTestResourcePath() + "zip/test1.txt"),
                new File(TestFileUtil.getTestResourcePath() + "zip/test2.txt")
        );
        
        ZipFile zipFile = new ZipFile(TestFileUtil.getTargetPath() + "zip/zip4j-multiCompressed.zip", "password".toCharArray());
        zipFile.addFiles(filesToAdd, zipParameters);
    }
    
    /**
     * 压缩目录
     */
    @Test
    void zipDir() throws ZipException {
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setEncryptFiles(true);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);
        
        ZipFile zipFile = new ZipFile(TestFileUtil.getTargetPath() + "zip/zip4j-dirCompressed.zip", "password".toCharArray());
        zipFile.addFolder(new File(TestFileUtil.getTestResourcePath() + "/zip"), zipParameters);
    }
    
    /**
     * 创建拆分的 zip
     */
    @Test
    void zipSplitFile() throws ZipException {
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setEncryptFiles(true);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);
        
        ZipFile zipFile = new ZipFile(TestFileUtil.getTargetPath() + "zip/zip4j-splitCompressed.zip", "password".toCharArray());
        int splitLength = 1024 * 1024 * 10; //10MB
        File sourceFile = new File(System.getProperty("user.dir")).getParentFile();
        zipFile.createSplitZipFileFromFolder(sourceFile, zipParameters, true, splitLength);
    }
    
    /**
     * 解压文件
     */
    @Test
    void unzip() throws ZipException {
        ZipFile zipFile = new ZipFile(TestFileUtil.getTargetPath() + "zip/zip4j-compressed.zip", "password".toCharArray());
        zipFile.extractAll(TestFileUtil.getTargetPath() + "zip/zip4j_destination_directory");
    }
    
    /**
     * 解压单个文件
     */
    @Test
    void unzipOneFile() throws ZipException {
        ZipFile zipFile = new ZipFile(TestFileUtil.getTargetPath() + "zip/zip4j-multiCompressed.zip", "password".toCharArray());
        zipFile.extractFile("test2.txt", TestFileUtil.getTargetPath() + "zip/zip4j_destination_directory_one_file");
    }
    
}
