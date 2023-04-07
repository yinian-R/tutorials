package com.wymm.io;

import cn.hutool.core.io.IoUtil;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Java 中的压缩和解压
 *
 * ZipOutputStream 用于以 ZIP 文件格式写入文件。
 * - putNextEntry() 开始编写新的 ZIP 文件条目，并将流定位到条目数据的开头。
 * - flush() 如果构建输出流 syncFlush 为 true 时，flush() 强制将待定数据冲洗到输出流，然后冲洗输出流
 * - closeEntry() 关闭当前条目并定位流以写入下一个数据
 */
public class _3ZipTest {
    
    /**
     * 压缩一个文件到 zip 中
     */
    @Test
    void zipOneFile() throws IOException {
        String sourceFile = TestFileUtil.getTestResourcePath() + "zip/test1.txt";
        String targetFile = TestFileUtil.getTargetPath() + "zip/compressed.zip";
        
        FileOutputStream fos = new FileOutputStream(targetFile);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        
        File fileToZip = new File(sourceFile);
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.close();
        fis.close();
        fos.close();
        
        System.out.println("see " + targetFile);
    }
    
    /**
     * 压缩多个文件到 zip 中
     */
    @Test
    void zipManyFile() throws IOException {
        List<String> srcFiles = Arrays.asList(
                TestFileUtil.getTestResourcePath() + "zip/test1.txt",
                TestFileUtil.getTestResourcePath() + "zip/test2.txt");
        String targetFile = TestFileUtil.getTargetPath() + "zip/multiCompressed.zip";
        
        FileOutputStream fos = new FileOutputStream(targetFile);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (String srcFile : srcFiles) {
            File fileToZip = new File(srcFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);
            
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();
        
        System.out.println("see " + targetFile);
    }
    
    /**
     * 压缩整个目录到 zip 中
     */
    @Test
    void zipDir() throws IOException {
        String sourceFile = TestFileUtil.getTestResourcePath();
        String targetFile = TestFileUtil.getTargetPath() + "zip/dirCompressed.zip";
        
        FileOutputStream fos = new FileOutputStream(targetFile);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile);
        zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();
        
        System.out.println("see " + targetFile);
    }
    
    /**
     * 为了压缩子目录，我们递归地遍历它们。
     * 每次我们找到一个目录时，我们将其名称附加到后代ZipEntry名称以保存层次结构。
     */
    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
    
    /**
     * 我们将 dirCompressed.zip 解压缩 到一个名为 unzipTest 的新文件夹中
     */
    @Test
    void unzip() throws IOException {
        String sourceFile = TestFileUtil.getTargetPath() + "zip/dirCompressed.zip";
        File destDir = new File(TestFileUtil.getTargetPath() + "zip/unzipTest");
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(sourceFile));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            // 在while循环中，我们将遍历每个ZipEntry并首先检查它是否为 directory。如果是，那么我们将使用mkdirs()方法创建目录；否则，我们将继续创建文件
            File newFile = newFile(destDir, zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()){
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }
    
                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len= zis.read(buffer)) > 0){
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }
    
    private File newFile(File destDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destDir, zipEntry.getName());
        
        String destDirPath = destDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
        
        if (!destFilePath.startsWith(destDirPath + File.separator)){
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
        return destFile;
    }
    
    /**
     * 动态压缩多个文本
     */
    @Test
    void zipManyText() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(outputStream);
    
        Map<String, String> pathAndContentMap = new HashMap<>();
        pathAndContentMap.put("/controller/controller.java", "a controller");
        pathAndContentMap.put("/service/service.java", "a service");
        pathAndContentMap.put("/mapper/mapper.java", "a mapper");
        for (Map.Entry<String, String> mapEntry : pathAndContentMap.entrySet()) {
            // 设置路径和文件名
            ZipEntry entry = new ZipEntry(mapEntry.getKey());
            zos.putNextEntry(entry);
            // 设置内容
            IoUtil.writeUtf8(zos, false, mapEntry.getValue());
            zos.flush();
            zos.closeEntry();
        }
    
        IoUtil.close(zos);
    
        // 压缩包内容
        byte[] data = outputStream.toByteArray();
    
        System.out.println();
    
        // 第一种：web 导出
        //IoUtil.write(response.getOutputStream(), false, data);
    
        
        // 第二种：导出到文件
        String targetFile = TestFileUtil.getTargetPath() + "zip/zipManyText.zip";
        FileOutputStream fos = new FileOutputStream(targetFile);
        IoUtil.write(fos, true, data);
    }
    
}
