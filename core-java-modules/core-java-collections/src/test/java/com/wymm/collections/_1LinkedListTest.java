package com.wymm.collections;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * LinkedList 实现了 List 的所有方法，所以下面示例不做过多演示
 * 在某些使用中，使用 LinkedList 会更加合适，例如：频繁插入、删除、修改
 */
class _1LinkedListTest {
    
    @Test
    void simple() {
        // 创建
        List<String> list = new LinkedList<>();
        
        // 获取 LinkedList 同步版本
        List<String> syncList = Collections.synchronizedList(new LinkedList<>());
        
        // 创建，因为 LinkedList 实现了 List 和 Deque，所以定义最好使用 LinkedList，方便使用 List 和 Deque 的方法
        LinkedList<Integer> linkedList = new LinkedList<>();
        IntStream.range(0, 100).forEach(linkedList::add);
         /*
         除了标准的add()和addAll()方法外，LinkedList还实现了List和Deque接口，您可以找到addFirst()和addLast()，分别在开头或结尾添加元素
         */
        
        // 删除第一个元素，列表没有数据异常 NoSuchElementException
        Object o = linkedList.removeFirst();
        // 删除最后一个元素，列表没有数据异常 NoSuchElementException
        Object o1 = linkedList.removeLast();
        // 删除第一次出现的这个元素
        boolean b = linkedList.removeFirstOccurrence(1);
        // 删除最后出现的这个元素
        boolean b1 = linkedList.removeLastOccurrence(2);
        
        
        // 检索第一个元素并将其从列表中删除，列表没有数据异常 NoSuchElementException
        Integer pop = linkedList.pop();
        // 检索第一个元素并将其从列表中删除，列表没有数据的时候返回 null
        Integer poll = linkedList.poll();
        Integer pollFirst = linkedList.pollFirst();
        Integer pollLast = linkedList.pollLast();
        
        // 它将元素插入为集合的尾部
        linkedList.add(100);
        // 它将元素插入为集合的头部
        linkedList.push(101);
    }
}
