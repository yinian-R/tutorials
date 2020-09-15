package com.wymm.io;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class RenameTest {

    @Test
    public void rename() throws Exception {
        Path path = Paths.get("D:\\BaiduNetdiskDownload\\Java");

        Stream<Path> pathStream = Files.find(path, 5, (p, attributes) -> {
            if (p.toString().contains(".flv")) {
                return true;
            }
            if (p.toFile().isDirectory()) {
                return false;
            }
            return false;
        });

        pathStream.forEach(pathFile -> {
            System.out.println(pathFile.toAbsolutePath());

            String replace = pathFile.toAbsolutePath().toString().replace("[高清 1080P]", "");
            File newFile = new File(replace);
            boolean result = pathFile.toFile().renameTo(newFile);
        });

    }
}
