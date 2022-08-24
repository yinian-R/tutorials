package com.wymm.io;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class TestFileUtil {
    
    /**
     * @return test resources 绝对路径
     */
    public static String getTestResourcePath() {
        String dir = System.getProperty("user.dir");
        return Stream.of(dir, "src", "test", "resources",File.separator).collect(Collectors.joining(File.separator));
    }
    
    public static String getTargetPath() {
        return TestFileUtil.class.getResource("/").getPath();
    }
    
}
