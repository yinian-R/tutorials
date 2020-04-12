package com.wymm.springbootsample;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class SimpleTest {
    /**
     * 获取文件，修改后缀
     */
    @Test
    public void test() throws Exception {
        Path path = Paths.get("E:\\workspace\\github\\advanced-sql-tutorial");
    
        Stream<Path> pathStream = Files.find(path, 5, (p,attributes)-> {
            if(p.toString().contains(".git")){
                return false;
            }
            if(p.toString().contains(".idea")){
                return false;
            }
            if(p.toFile().isDirectory()){
                return false;
            }
            return true;
        });
    
        pathStream.forEach(pathFile -> {
            System.out.println(pathFile.toAbsolutePath());
    
            String replace = pathFile.toAbsolutePath().toString().replace(".txt", ".sql");
            File newFile = new File(replace);
            pathFile.toFile().renameTo(newFile);
        });
    
    }
}
