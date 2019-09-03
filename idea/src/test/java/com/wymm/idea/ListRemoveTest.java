package com.wymm.idea;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class ListRemoveTest {
    
    @Test
    public void test() {
        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        list.add("dd");
        list.add("ee");
        
        
        // throw ConcurrentModificationException
        //for (String str : list) {
        //    System.out.println(str);
        //    if (str.equals("bb")) {
        //        list.remove(str);
        //    }
        //}
        
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println(next);
            if (next.equals("bb")) {
                iterator.remove();
            }
        }
        System.out.println(iterator);
        
    }
}
