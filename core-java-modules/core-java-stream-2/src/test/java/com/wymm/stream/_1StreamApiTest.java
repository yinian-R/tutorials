package com.wymm.stream;

import com.wymm.stream._1stream.Product;
import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Stream 基础 API
 * Stream API 是一套功能强大但易于理解的用于处理元素序列的工具。如果使用得当，它可以让我们减少大量样板代码，创建更具可读性的程序，并提高应用程序的生产力。
 *
 * 在本文中显示的大多数代码示例中，我们没有使用流（我们没有应用close()方法或终端操作）。
 * 在真正的应用程序中，不要让实例化的流未被使用，因为这会导致内存泄漏。
 */
public class _1StreamApiTest {
    static final List<Product> productList = Arrays.asList(new Product(23, "potatoes"),
            new Product(14, "orange"), new Product(13, "lemon"),
            new Product(23, "bread"), new Product(13, "sugar"));
    
    @Test
    void api() throws IOException {
        // 空流
        Stream<String> streamEmpty = Stream.empty();
        
        
        // 集合流
        Collection<String> collection = Arrays.asList("a", "b", "c");
        Stream<String> streamOfCollection = collection.stream();
        
        
        // 数组流
        Stream<String> streamOfArray = Stream.of("a", "b", "c");
        String[] arr = new String[]{"a", "b", "c"};
        Stream<String> streamOfArrayFull = Arrays.stream(arr);
        Stream<String> streamOfArrayPart = Arrays.stream(arr, 1, 3);
        
        
        // Stream.builder()
        Stream<String> streamBuilder =
                Stream.<String>builder().add("a").add("b").add("c").build();
        
        
        // Stream.generate()
        Stream<String> streamGenerated =
                Stream.generate(() -> "element").limit(10);
        
        
        // 流迭代
        // 第一个元素是iterate()方法的第一个参数。后续创建每个元素时，指定的函数将应用于前一个元素。在上面的示例中，第二个元素将是 42
        Stream<Integer> streamIterated = Stream.iterate(40, n -> n + 2).limit(20);
        
        
        // 基础类型流
        // Java 8 提供了使用三种基本类型创建流的可能性：int、long和double。
        // 由于Stream<T>是一个泛型接口，并且无法将基础类型用作泛型的类型参数，因此创建了三个新的特殊接口：IntStream、LongStream、DoubleStream。
        IntStream intStream = IntStream.range(1, 3);
        LongStream longStream = LongStream.rangeClosed(1, 3);
        Random random = new Random();
        DoubleStream doubleStream = random.doubles(3);
        
        
        // 字符流
        IntStream streamOfChars = "abc".chars();
        Stream<String> streamOfString =
                Pattern.compile(", ").splitAsStream("a, b, c");
        
        
        // 文件流
        Path path = Paths.get("C:\\file.txt");
        Stream<String> streamOfStrings = Files.lines(path);
        Stream<String> streamWithCharset = Files.lines(path, StandardCharsets.UTF_8);
        
        
        // 引用流
        Stream<String> stream =
                Stream.of("a", "b", "c").filter(element -> element.contains("b"));
        Optional<String> anyElement = stream.findAny();
        // 以下代码触发 IllegalStateException
        //Optional<String> firstElement = stream.findFirst();
        // 所以记住Java 8流不能被重用是非常重要的
        
        List<String> elements =
                Stream.of("a", "b", "c").filter(element -> element.contains("b"))
                        .collect(Collectors.toList());
        Optional<String> anyElement1 = elements.stream().findAny();
        Optional<String> firstElement = elements.stream().findFirst();
    }
    
    /**
     * 使用 reduce() ，必须使用并行流才能生效
     *  reduce(int identity, IntBinaryOperator op); 第一个参数是起始值、第二个参数是累加器函数
     */
    @Test
    void reduce() {
        OptionalInt reduced =
                IntStream.range(1, 4).reduce((a, b) -> a + b);
        
        // reduce=16 (1 + 2 + 3)
        int reducedTwoParams =
                IntStream.range(1, 4).reduce(10, (a, b) -> a + b);
        
        
        // reduce=16 (1 + 2 + 3)，结果和前面示例（16）相同，这意味着 combiner 没有被调用
        int reducedParams = Stream.of(1, 2, 3)
                .reduce(10, (a, b) -> a + b, (a, b) -> {
                    // 本方法并没有被调用
                    System.out.println("combiner was called");
                    return a + b;
                });
        
        // 要使 combiner 工作，流应该是并行的
        int reducedParallel = Arrays.asList(1, 2, 3).parallelStream()
                .reduce(10, (a, b) -> a + b, (a, b) -> {
                    System.out.println("combiner was called");
                    return a + b;
                });
        assertEquals(36, reducedParallel);
        // 这里的结果不同（36），并且组合器被调用了两次。
        // 这里通过以下算法进行归约：累加器通过将流的每个元素添加到identity运行了 3 次。这些行动是并行进行的。
        // 结果，它们有 (10 + 1 = 11; 10 + 2 = 12; 10 + 3 = 13;)
        // 现在 combiner 可以合并这三个结果。它需要两次迭代（12 + 13 = 25；25 + 11 = 36）
    }
    
    @Test
    void collect() {
        // 将流转换为集合
        List<String> collectorCollection = productList.stream()
                .map(Product::getName)
                .collect(Collectors.toList());
        
        // 还原为字符串
        // joining()有三个参数（分隔符、前缀、后缀）
        String listToString = productList.stream()
                .map(Product::getName)
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("listToString:" + listToString);
        
        // 处理流所有数值，获取平均值
        Double averagePrice = productList.stream()
                .collect(Collectors.averagingInt(Product::getPrice));
        
        // 处理流所有数值，获取总和
        Integer summingPrice = productList.stream()
                .collect(Collectors.summingInt(Product::getPrice));
        
        // 收集有关流元素的统计信息
        IntSummaryStatistics summarizingPrice = productList.stream()
                .collect(Collectors.summarizingInt(Product::getPrice));
        System.out.println("summarizingPrice:" + summarizingPrice);
        
        // 根据指定功能对流的元素进行分组
        Map<Integer, List<Product>> collectorMapOfList = productList.stream()
                .collect(Collectors.groupingBy(Product::getPrice));
        MapUtils.verbosePrint(System.out, "分组", collectorMapOfList);
        
        // 根据谓词将流的元素分组
        Map<Boolean, List<Product>> mapPartition = productList.stream()
                .collect(Collectors.partitioningBy(v -> v.getPrice() > 15));
        MapUtils.verbosePrint(System.out, "谓词分组", mapPartition);
        
        // 收集器执行额外的转换
        Set<Product> unmodifiableSet = productList.stream()
                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
        
        // 自定义收集器
        // 如果我们想编写自己的 Collector 实现，我们需要实现 Collector 接口，并指定它的三个泛型参数：
        // T – 可用于收集的对象类型
        // A – 可变累加器对象的类型
        // R – 最终结果的类型
        // 如果出于某种原因应该创建自定义收集器，那么最简单且最简洁的方法是使用类型为 Collector 的 of 方法
        Collector<Product, LinkedList<Product>, LinkedList<Product>> toLinkedList =
                Collector.of(LinkedList::new, LinkedList::add, (left, right) -> {
                    left.addAll(right);
                    return left;
                });
        LinkedList<Product> linkedListOfPersons = productList.stream()
                .collect(toLinkedList);
        System.out.println("linkedListOfPersons:" + linkedListOfPersons);
    }
    
    /**
     * 并行流
     * 在底层，Stream API 自动使用ForkJoin框架来并行执行操作
     */
    @Test
    void parallel() {
        // 当流的来源是Collection或array时，可以借助parallelStream()来创建并行流
        Stream<Product> streamOfCollection = productList.parallelStream();
        boolean isParallel = streamOfCollection.isParallel();
        boolean bigPrice = streamOfCollection
                .map(product -> product.getPrice() * 12)
                .anyMatch(price -> price > 200);
        
        // 如果流的来源不是Collection或array，则应使用parallel()方法
        IntStream intStreamParallel = IntStream.range(1, 150).parallel();
        
        // 并行模式下的流可以使用sequential()方法转换回顺序模式
        IntStream intStreamSequential = intStreamParallel.sequential();
    }
}
