package com.wymm.io;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * apache common compress 压缩及解压各种格式文件
 * 官方示例 https://commons.apache.org/proper/commons-compress/examples.html
 */
public class _5CompressTest {
    
    /**
     * ZIP 压缩
     */
    @Test
    void zip() throws IOException {
        File archive = new File(TestFileUtil.getTargetPath() + "zip/compress-compressed.zip");
        List<File> filesToArchive = Stream.of(
                TestFileUtil.getTestResourcePath() + "zip/test1.txt",
                TestFileUtil.getTestResourcePath() + "zip/test2.txt")
                .map(File::new)
                .collect(Collectors.toList());
    
        zip(archive, filesToArchive);
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
    
    /**
     * ZIP 解压
     */
    @Test
    void unzip() throws IOException {
        File archive = new File(TestFileUtil.getTargetPath() + "zip/dist.zip");
        String targetDir = TestFileUtil.getTargetPath() + "zip/compress-compressed";
        unzip(archive, targetDir);
    }
    
    public static void unzip(File archive, String targetDir) throws IOException {
        // gbk 支持压缩文件包含中文
        try (ArchiveInputStream i = new ZipArchiveInputStream( new FileInputStream(archive), "GBK")) {
            ArchiveEntry entry;
            while ((entry = i.getNextEntry()) != null) {
                if (!i.canReadEntryData(entry)) {
                    continue;
                }
                String name = targetDir + File.separator + entry.getName();
                File f = new File(name);
                if (entry.isDirectory()) {
                    if (!f.isDirectory() && !f.mkdirs()) {
                        throw new IOException("failed to create directory " + f);
                    }
                } else {
                    File parent = f.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("failed to create directory " + parent);
                    }
                    try (OutputStream o = Files.newOutputStream(f.toPath())) {
                        IOUtils.copy(i, o);
                    }
                }
            }
        }
    }
    
}
