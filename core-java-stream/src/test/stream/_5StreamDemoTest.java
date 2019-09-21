package stream;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class _5StreamDemoTest {
    /**
     * 生成占位符
     */
    @Test
    void generatePlaceholders(){
        List<String> list = new ArrayList<>();
        list.add("asd");
        list.add("asd");
        list.add("asd");
        Optional<String> placeholders = Stream.generate(()->"?").limit(list.size()).reduce((a, b)-> a+","+b);
        System.out.println(placeholders.orElse(""));
        
    }
}