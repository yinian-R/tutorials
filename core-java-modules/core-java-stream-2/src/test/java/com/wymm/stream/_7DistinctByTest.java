package com.wymm.stream;

import com.wymm.stream._7distinctby.Person;
import one.util.streamex.StreamEx;
import org.eclipse.collections.impl.block.factory.HashingStrategies;
import org.eclipse.collections.impl.utility.ListIterate;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 使用列表中对象的特定属性过滤集合的不同方法
 */
public class _7DistinctByTest {
    
    static List<Person> LIST = Arrays.asList(new Person("A", 18), new Person("A", 20), new Person("B", 22));
    
    /**
     * 使用 Stream API
     * <p>
     * Stream API 提供distinct()方法，该方法基于Object类的equals()方法返回列表的不同元素。
     * 但是，如果我们想按特定属性进行过滤，它就会变得不那么灵活。我们的替代方法之一是编写一个保持状态的过滤器。
     */
    @Test
    void usingStreamApi() {
        List<Person> distinctList = LIST.stream()
                .filter(distinctByKey(Person::getName))
                .collect(Collectors.toList());
        System.out.println(distinctList);
    }
    
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        System.out.println("call distinctByKey");
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    
    /**
     * 使用 vavr
     */
    @Test
    void usingVavr() {
        List<Person> distinctList = io.vavr.collection.List.ofAll(LIST)
                .distinctBy(Person::getName)
                .toJavaList();
        System.out.println(distinctList);
    }
    
    /**
     * 使用 StreamEx
     */
    @Test
    void usingStreamEx() {
        List<Person> distinctList = StreamEx.of(LIST)
                .distinct(Person::getName)
                .toList();
        System.out.println(distinctList);
    }
}
