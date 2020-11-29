package com.wymm.fragment;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;


/**
 * BitSet 位集合类实现了一组可以单独设置和清除的位或标志
 *
 * 为了存储和操作位数组，可能有人认为我们应该使用 boolean [] 作为我们的数据结构。乍一看，这似乎是一个合理的建议。
 * 但是，boolean [] 中的每个 布尔 成员 通常消耗一个字节，而不是一个bit。因此，当我们对内存有严格的要求，或者只是为了减少内存占用时，boolean [] 并不是理想的选择。
 * <p>
 * 为了解决此问题，我们可以结合使用数字数据类型（例如long）和按位运算。那就是BitSet 进来的地方
 */
public class _3BitSetTest {
    
    /**
     * 打印内存占用
     */
    @Test
    public void print() {
        boolean[] bits = new boolean[1024];
        System.out.println(ClassLayout.parseInstance(bits).toPrintable());
        
        BitSet bitSet = new BitSet(1024);
        System.out.println(GraphLayout.parseInstance(bitSet).toPrintable());
    }
    
    /**
     * BitSet API 示例
     */
    @Test
    void usingBitSet() {
        BitSet bitSet = new BitSet();
        // 特定索引的值设置为 true
        bitSet.set(10);
        assertTrue(bitSet.get(10));
        
        
        // 将一定范围的位设置为 true
        bitSet.set(20, 30);
        for (int i = 20; i <= 29; i++) {
            assertTrue(bitSet.get(i));
        }
        assertFalse(bitSet.get(30));
        
        // 将特定的位索引设置为 false
        bitSet.set(10, false);
        assertFalse(bitSet.get(10));
        
        // 将一定范围的位设置为 false
        bitSet.set(20, 30, false);
        for (int i = 20; i <= 29; i++) {
            assertFalse(bitSet.get(i));
        }
        
        
        bitSet.set(42);
        assertTrue(bitSet.get(42));
        // 除了将特定的位索引设置为 false之外，我们还可以使用clear（index）方法将其清除
        bitSet.clear(42);
        assertFalse(bitSet.get(42));
        
        
        bitSet.set(10, 20);
        for (int i = 10; i < 20; i++) {
            assertTrue(bitSet.get(i));
        }
        // 清除一系列位
        bitSet.clear(10, 20);
        for (int i = 10; i < 20; i++) {
            assertFalse(bitSet.get(i));
        }
        
        
        bitSet.set(10, 20);
        // 如果我们在不传递任何参数的情况下调用此方法，它将清除所有设置的位
        bitSet.clear();
        for (int i = 0; i < 100; i++) {
            assertFalse(bitSet.get(i));
        }
        
        
        bitSet.set(42);
        // 使用了 get（index）方法。设置请求的位索引后，此方法将返回 true。否则，它将返回 false
        assertTrue(bitSet.get(42));
        assertFalse(bitSet.get(43));
        
        
        bitSet.set(10, 21);
        // 可以使用get（fromInclusive，toExclusive）方法获得一系列位索引
        BitSet newBitSet = bitSet.get(10, 20);
        for (int i = 0; i < 10; i++) {
            assertTrue(newBitSet.get(i));
        }
        
        bitSet.set(42);
        // 要取反当前的位索引值，可以使用 flip（index）方法。也就是说，它将把 true 值变成 false ，反之亦然
        bitSet.flip(42);
        assertFalse(bitSet.get(42));
        
        bitSet.set(12, false);
        bitSet.flip(12);
        assertTrue(bitSet.get(12));
        
        // 可以使用flip（fromInclusive，toExclusive）方法在一系列值上实现相同的 目的
        bitSet.flip(30, 40);
        for (int i = 30; i < 40; i++) {
            assertTrue(bitSet.get(i));
        }
        
        
        BitSet defaultBitSet = new BitSet();
        // 方法返回位内部阵列可表示的数目
        assertEquals(64, defaultBitSet.size());
        
        
        bitSet = new BitSet();
        // cardinality（）方法表示BitSet中设置的位数
        assertEquals(0, bitSet.cardinality());
        bitSet.set(10, 30);
        assertEquals(30 - 10, bitSet.cardinality());
        // 首先，由于所有位均为false，因此此方法返回零 。将[10，30）范围设置为 true后，  cardinality（） 方法调用将返回20
        // 同样，  length（） 方法返回最后一个设置位的索引之后的一个索引
        assertEquals(30, bitSet.length());
        bitSet.set(100);
        assertEquals(101, bitSet.length());
        
        // 当BitSet中至少有一个设置位时,isEmpty（）方法返回 false。否则，它将返回 true
        assertFalse(bitSet.isEmpty());
        bitSet.clear();
        assertTrue(bitSet.isEmpty());
        
        
        BitSet first = new BitSet();
        first.set(5, 10);
        BitSet second = new BitSet();
        second.set(7, 15);
        // intersects（BitSet）方法接受另一个位集，当两个位集有共同点时返回true
        assertTrue(first.intersects(second));
        
        // 也可以对两个位集执行逻辑与运算
        first.and(second);
        assertTrue(first.get(7));
        assertTrue(first.get(8));
        assertTrue(first.get(9));
        assertFalse(first.get(10));
        
        
        first.clear();
        first.set(5, 10);
        // 同样，我们也可以对两个位集执行逻辑异或
        first.xor(second);
        for (int i = 5; i < 7; i++) {
            assertTrue(first.get(i));
        }
        for (int i = 10; i < 15; i++) {
            assertTrue(first.get(i));
        }
        // 还有其他方法，例如 andNot（BitSet）或 or（BitSet）， 它们可以对两个BitSet执行其他逻辑运算
        
        
        bitSet = new BitSet();
        bitSet.set(15, 25);
        // nextSetBit（fromIndex）方法将返回从起始下一组比特索引 的fromIndex
        assertEquals(15, bitSet.nextSetBit(13));
        // fromIndex 本身包含在这个计算中。当BitSet中没有剩余的 true 位时 ，它将返回-1
        assertEquals(-1, bitSet.nextSetBit(25));
        
        byte[] bytes = bitSet.toByteArray();
        long[] longs = bitSet.toLongArray();
    }
}
