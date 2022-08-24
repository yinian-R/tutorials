package com.wymm.easyexcelsample._8WriteZip;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ZipUtil;
import com.alibaba.excel.EasyExcel;
import com.wymm.easyexcelsample.excel.DemoData;
import com.wymm.easyexcelsample.excel.TestFileUtil;
import com.wymm.easyexcelsample.utils.FileDownloadUtils;
import com.wymm.easyexcelsample.utils.ZipFile;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 导出多个文件到本地，并压缩成 zip
 */
@Slf4j
@RestController
public class _8WriteZipTest {
    
    /**
     * 导出压缩文件
     */
    @GetMapping("downloadZip")
    void exportManyExcel_thenZip(HttpServletResponse response) throws IOException {
        String tempPath = null;
        try {
            // 创建临时目录
            tempPath = FileDownloadUtils.mkdirFileTempPathRandomChild().getAbsolutePath();
            log.info("mkdir " + tempPath);
            
            // 导出 excel 文件到临时目录
            String filePath = tempPath + File.separator + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
            EasyExcel.write(filePath, DemoData.class)
                    .sheet("模板")
                    .doWrite(TestFileUtil.data());
            filePath = tempPath + File.separator + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
            EasyExcel.write(filePath, DemoData.class)
                    .sheet("模板")
                    .doWrite(TestFileUtil.data());
            
            // 创建压缩文件 zip 到临时目录
            String zipFilePath = tempPath + File.separator + RandomUtil.randomString(20) + ".zip";
            File zipFile = new File(zipFilePath);
            List<File> excelFiles = FileUtil.loopFiles(tempPath);
            ZipUtil.zip(zipFile, true, excelFiles.toArray(new File[0]));
            
            // 导出文件流
            FileDownloadUtils.exportFile(response, "多个excel导出", zipFile);
        } finally {
            if (tempPath != null) {
                // 删除临时目录
                FileUtil.del(tempPath);
            }
        }
    }
    
    /**
     * 封装了 ZipFile 导出压缩文件
     */
    @GetMapping("downloadZipSimple")
    void downloadZipSimple(HttpServletResponse response) throws IOException {
        String tempPatentPath = null;
        try (ZipFile zipFile = new ZipFile()) {
            // 创建临时目录
            tempPatentPath = FileDownloadUtils.mkdirFileTempPathRandomChild().getAbsolutePath();
            
            // 导出 excel 文件到临时目录
            String filePath = tempPatentPath + File.separator + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
            EasyExcel.write(filePath, DemoData.class)
                    .sheet("模板")
                    .doWrite(TestFileUtil.data());
            zipFile.addFile(filePath);
            filePath = tempPatentPath + File.separator + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
            EasyExcel.write(filePath, DemoData.class)
                    .sheet("模板")
                    .doWrite(TestFileUtil.data());
            zipFile.addFile(filePath);
            
            
            // web 导出压缩文件
            zipFile.export(response, "测试压缩文件");
        } finally {
            FileUtil.del(tempPatentPath);
        }
    }
}
