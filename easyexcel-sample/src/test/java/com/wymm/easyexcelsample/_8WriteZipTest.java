package com.wymm.easyexcelsample;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.excel.EasyExcel;
import com.wymm.easyexcelsample.excel.DemoData;
import com.wymm.easyexcelsample.excel.TestFileUtil;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 导出多个文件到本地，并压缩成 zip
 */
public class _8WriteZipTest {
    
    @Test
    void exportManyExcel_thenZip() throws IOException {
        // 临时目录根路径
        String fileTempPath = TestFileUtil.getPath();
        // 创建临时目录
        String tempPath = fileTempPath + File.separator + RandomUtil.randomString(20);
        FileUtil.mkdir(tempPath);
        
        // 导出 excel 文件到临时目录
        List<String> files = new ArrayList<>();
        String fileName = tempPath + File.separator + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class)
                .sheet("模板")
                .doWrite(TestFileUtil.data());
        files.add(fileName);
        fileName = tempPath + File.separator + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class)
                .sheet("模板")
                .doWrite(TestFileUtil.data());
        files.add(fileName);
        
        // 创建压缩文件 zip 到临时目录
        List<File> filesToArchive = files.stream()
                .map(File::new)
                .collect(Collectors.toList());
        File zipFile = new File(tempPath + File.separator + "manyWrite.zip");
        zip(zipFile, filesToArchive);
        // 获取压缩文件流
        
        // do something
        
        // 删除临时目录
        //FileUtil.del(tempPath);
    }
    
    public static void zip(File archive, List<File> filesToArchive) throws IOException {
        try (ArchiveOutputStream o = new ZipArchiveOutputStream(archive)) {
            for (File f : filesToArchive) {
                ArchiveEntry entry = o.createArchiveEntry(f, f.getName());
                o.putArchiveEntry(entry);
                if (f.isFile()) {
                    try (InputStream i = Files.newInputStream(f.toPath())) {
                        IOUtils.copy(i, o);
                    }
                }
                o.closeArchiveEntry();
            }
            o.finish();
        }
    }
    
}
