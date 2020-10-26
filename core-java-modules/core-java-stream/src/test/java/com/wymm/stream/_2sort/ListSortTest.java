package com.wymm.stream._2sort;

import com.wymm._2sort.Human;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 对集合进行排序
 * <p>
 * Comparator 返回 int，负整数表示入参1比入参2小，0表示相等，正整数表示入参1比入参2大
 * <p>
 * 强烈建议使用nullsFirst() 或 nullsLast() 装饰器，因为它们更灵活，尤其是更具可读性
 * <p>
 * 推荐使用Java 8 Lambda表达式对List进行排序
 */
class ListSortTest {
    
    /**
     * 使用简短的 Lamdba 进行排序
     */
    @Test
    void givenLambdaShortForm_whenSortingEntitiesByName_thenCorrectlySorted() {
        List<Human> humans = new ArrayList<Human>() {
            {
                add(new Human("Sarah", 10));
                add(new Human("Jack", 12));
            }
        };
        
        humans.sort((h1, h2) -> h1.getName().compareTo(h2.getName()));
        assertEquals(humans.get(0), new Human("Jack", 12));
    }
    
    /**
     * 使用静态方法进行排序
     */
    @Test
    void givenMethodDefinition_whenSortingEntitiesByNameThenAge_thenCorrectlySorted() {
        List<Human> humans = new ArrayList<Human>() {
            {
                add(new Human("Sarah", 10));
                add(new Human("Jack", 12));
            }
        };
        
        humans.sort(Human::compareByNameThenAge);
        assertEquals(humans.get(0), new Human("Jack", 12));
    }
    
    /**
     * 使用 Comparator.comparing 方法来定义比较逻辑
     */
    @Test
    void useComparator_whenSortingEntitiesByName_thenCorrectlySorted() {
        List<Human> humans = new ArrayList<Human>() {
            {
                add(new Human("Sarah", 10));
                add(new Human("Jack", 12));
            }
        };
        
        Collections.sort(humans, Comparator.comparing(Human::getName));
        assertEquals(humans.get(0), new Human("Jack", 12));
    }
    
    /**
     * 使用反转比较器
     */
    @Test
    void whenSortingEntitiesByNameReversed_thenCorrectlySorted() {
        List<Human> humans = new ArrayList<Human>() {
            {
                add(new Human("Sarah", 10));
                add(new Human("Jack", 12));
            }
        };
        
        Comparator<Human> comparator = (h1, h2) -> h1.getName().compareTo(h2.getName());
        humans.sort(comparator.reversed());
        assertEquals(humans.get(0), new Human("Sarah", 10));
    }
    
    /**
     * 使用多个条件组合排序
     */
    @Test
    void givenComposition_whenSortingEntitiesByNameThenAge_thenCorrectlySored() {
        List<Human> humans = new ArrayList<Human>() {
            {
                add(new Human("Sarah", 12));
                add(new Human("Sarah", 10));
                add(new Human("Zack", 12));
            }
        };
        
        humans.sort(Comparator.comparing(Human::getName).thenComparing(Human::getAge));
        assertEquals(humans.get(0), new Human("Sarah", 10));
    }
    
    
    /**
     * 使用 Stream sored() 对集合进行排序
     */
    @Test
    void givenStreamNaturalOrdering_whenSortingEntitiesByName_thenCorrectlySorted() {
        List<String> letters = new ArrayList<String>() {
            {
                add("B");
                add("A");
                add("C");
            }
        };
        
        List<String> sortedLetters = letters.stream().sorted().collect(Collectors.toList());
        assertEquals(sortedLetters.get(0), "A");
    }
    
    /**
     * 使用 Stream sored() 对集合进行排序
     */
    @Test
    void givenStreamCustomOrdering_whenSortingEntitiesByName_thenCorrectlySorted() {
        List<Human> humans = new ArrayList<Human>() {
            {
                add(new Human("Sarah", 10));
                add(new Human("Jack", 12));
            }
        };
        Comparator<Human> nameComparator = (h1, h2) -> h1.getName().compareTo(h2.getName());
        
        List<Human> sortedHumans = humans.stream().sorted(nameComparator).collect(Collectors.toList());
        assertEquals(sortedHumans.get(0), new Human("Jack", 12));
    }
    
    /**
     * Stream 使用 Comparator.comparing 进一步简化上述示例
     */
    @Test
    void givenStreamComparatorOrdering_whenSortingEntitiesByName_thenCorrectlySorted() {
        List<Human> humans = new ArrayList<Human>() {
            {
                add(new Human("Sarah", 10));
                add(new Human("Jack", 12));
            }
        };
        
        List<Human> sortedHumans = humans.stream()
                .sorted(Comparator.comparing(Human::getName))
                .collect(Collectors.toList());
        
        assertEquals(sortedHumans.get(0), new Human("Jack", 12));
    }
    
    
    /**
     * Stream 使用 Comparator.reverseOrder() 以自然相反的顺序对列表进行排序
     */
    @Test
    void givenStreamNaturalOrdering_whenSortingEntitiesByNameReversed_thenCorrectlySorted() {
        List<String> letters = new ArrayList<String>() {
            {
                add("B");
                add("A");
                add("C");
            }
        };
        
        List<String> reverseSortedLetters = letters.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        
        assertEquals(reverseSortedLetters.get(0), "C");
    }
    
    /**
     * Stream 使用自定义 Comparator 以相反的顺序对列表进行排序
     */
    @Test
    void givenStreamCustomOrdering_whenSortingEntitiesByNameReversed_thenCorrectlySorted() {
        List<Human> humans = new ArrayList<Human>() {
            {
                add(new Human("Sarah", 10));
                add(new Human("Jack", 12));
            }
        };
        Comparator<Human> reverseNameComparator = (h1, h2) -> h2.getName().compareTo(h1.getName());
        
        List<Human> reverseSortedHumans = humans.stream().sorted(reverseNameComparator)
                .collect(Collectors.toList());
        assertEquals(reverseSortedHumans.get(0), new Human("Sarah", 10));
    }
    
    /**
     * Stream 使用 Comparator.reverseOrder() 以相反的顺序对列表进行排序
     */
    @Test
    void givenStreamComparatorOrdering_whenSortingEntitiesByNameReversed_thenCorrectlySorted() {
        List<Human> humans = new ArrayList<Human>() {
            {
                add(new Human("Sarah", 10));
                add(new Human("Jack", 12));
            }
        };
        
        List<Human> reverseSortedHumans = humans.stream()
                .sorted(Comparator.comparing(Human::getName, Comparator.reverseOrder()))
                .collect(Collectors.toList());
        
        assertEquals(reverseSortedHumans.get(0), new Human("Sarah", 10));
    }
    
    
    /**
     * 我们以无法对包含空 值的集合进行排序的方式实现了Comparator。
     * 也就是说，如果集合包含至少一个 null元素，则 sort方法将引发NullPointerException
     */
    @Test
    void givenANullElement_whenSortingEntitiesByName_thenThrowsNPE() {
        List<Human> humans = new ArrayList<Human>() {
            {
                add(null);
                add(new Human("Jack", 12));
            }
        };
        
        humans.sort((h1, h2) -> h1.getName().compareTo(h2.getName()));
    }
    
    /**
     * 最简单的解决方案就是 Comparator 实现手动处理 null
     */
    @Test
    void givenANullElement_whenSortingEntitiesByNameManually_thenMovesTheNullToLast() {
        List<Human> humans = new ArrayList<Human>() {
            {
                add(null);
                add(new Human("Jack", 12));
                add(null);
            }
        };
        
        humans.sort((h1, h2) -> {
            if (h1 == null) {
                return h2 == null ? 0 : 1;
            } else if (h2 == null) {
                return -1;
            }
            return h1.getName().compareTo(h2.getName());
        });
        
        assertNotNull(humans.get(0));
        assertNull(humans.get(1));
        assertNull(humans.get(2));
    }
    
    /**
     * 是不是空安全进入Comparator.nullsLast()方法，并达到相同的结果
     */
    @Test
    void givenANullElement_whenSortingEntitiesByName_thenMovesTheNullToLast() {
        List<Human> humans = new ArrayList<Human>() {
            {
                add(null);
                add(new Human("Jack", 12));
                add(null);
            }
        };
        
        humans.sort(Comparator.nullsLast(Comparator.comparing(Human::getName)));
        
        assertNotNull(humans.get(0));
        assertNull(humans.get(1));
        assertNull(humans.get(2));
    }
    
    /**
     * 使用 Comparator.nullsFirst() 将 null 元素移到集合的开头
     */
    @Test
    public void givenANullElement_whenSortingEntitiesByName_thenMovesTheNullToStart() {
        List<Human> humans = new ArrayList<Human>() {
            {
                add(null);
                add(new Human("Jack", 12));
                add(null);
            }
        };
        
        humans.sort(Comparator.nullsFirst(Comparator.comparing(Human::getName)));
        
        assertNull(humans.get(0));
        assertNull(humans.get(1));
        assertNotNull(humans.get(2));
    }
    
}
