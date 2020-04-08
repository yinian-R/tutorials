package stream;

import java.util.Random;
import java.util.stream.Stream;

/**
 * Stream 中间操作
 */
public class _4StreamDemo {
    public static void main(String[] args) {
        String str = "my name is word";

        // 把单词长度大于2的长度打印出来
        Stream.of(str.split(" ")).filter(s -> s.length() > 2).map(String::length).forEach(System.out::println);

        System.out.println("");
        // flatMap 适合A里面有B属性，B属性是一个集合，最终得到A元素里面的所有B属性集合
        // intStream/longStream 并不是Stream的子类，所以要进行装箱 boxed
        Stream.of(str.split(" ")).flatMap(s -> s.chars().boxed()).forEach(i -> System.out.println((char) i.intValue()));

        System.out.println("");
        // peek 用于debug。消费数据
        Stream.of(str.split(" ")).peek(System.out::println).forEach(System.out::println);

        System.out.println("");
        new Random().ints().limit(10).forEach(System.out::println);
    }
}
