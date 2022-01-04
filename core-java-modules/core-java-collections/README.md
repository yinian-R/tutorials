# Collections

## LinkedList

> LinkedList 是 List 和 Deque 接口的实现，它实现所有可选列表操作并且允许所有元素（包括null）

### LinkedList 最重要属性

以下是 LinkedList 最重要属性：
- 索引到列表中的操作将从开头或结尾开始遍历列表，已更接近指定索引的位置为准
- 不同步
- 它的迭代器是 fail-fast（这意味着在创建迭代器后删除元素，将引起异常ConcurrentModificationException）
- 每一个元素都是节点，持有上一个和下一个的引用
- 保持插入顺序

尽管 LinkedList 是不同步的，但是我们可以使用 Collections.synchronizedList(new LinkedList(...)) 来创建它的同步版本

### LinkedList 与 ArrayList 比较：

*结构*

ArrayList 是基于索引和数据的阵列，检索的执行时间是O(1)

另一方面，LinkedList 将其数据存储为元素列表，每个元素都链接上一个和下一下元素。在这种情况下，检索操作的执行时间是O(n) 

*运行方式*

在 LinkedList 中，插入和删除操作更快，因为在添加元素到集合中的任何位置时，无需调整数组的大小或更新索引，仅更改元素周围的引用即可

*内存使用情况*

LinkedList 比 ArrayList 更消耗内存，因为 LinkedList 在每个元素都存储了两个引用，一个用于它的前一个元素，一个用于它的后一个元素，而 ArrayList 仅存储数据和索引

### 结论

一般 ArrayList 是 List 的默认实现

但是，在某些使用中，使用 LinkedList 会更加合适，例如：频繁插入、删除、修改

## HashSet 与 TreeSet 比较：

与 HashSet 相比，TreeSet 的性能较低。像添加、删除和搜索这样的操作需要O(log n)时间，而像按排序顺序打印n个元素这样的操作需要O(n)时间。
如果我们想保持数据的排序，TreeSet 应该是我们的主要选择，因为 TreeSet 可以按升序或降序进行访问和遍历。

### HashSet

HashSet 性能主要受以下两个参数影响：初始容量和负载因子

将元素添加到集合中的预期时间复杂度为 O(1)，在最坏的情况下（仅存在一个存储桶），该复杂度可能降至O(n)。因此，保持正确的HashSet容量至关重要。
重要说明：自JDK 8起，最差的时间复杂度为O(log n)。（因为存储桶长度超过8个结构改为平衡树）

低的初始容量会降低空间复杂性，但会增加重新哈希的频率，这是一个昂贵的过程。另一方面，高的初始容量会增加迭代成本和初始内存消耗。

- 高的初始容量对于大量的 entries 以及很少甚至没有迭代的
- 低的初始容量适合于迭代次数多的少数 entries

## HashMap

HashMap 的插入和检索的时间复杂度都是O(1)

HashMap 将元素存储在所谓的 buckets 中，buckets 的数量称为 Capacity。
当我们往map中add一个值的时候，键的hashCode()方法用于确定存储在其中的buckets。
为了检索该值，HashMap使用相同的方式 (hashCode()) 计算 buckets。然后遍历在该 bucket 中找到对象，并使用建的 equals() 方法找到完全匹配的对象。

我们应该使用不可变的键，因为一旦键改变了，我们将不能获取相应的值，而是返回 null

### 碰撞
为了使键能正常工作，相同的键必须具有相同的哈希，但是不同的键也可以具有相同的哈希。
如果两个不同的键具有相同的哈希，则属于他们的值存储在同一个 bucket 中。
在 bucket 中，值存储在列表中，并通过遍历列表来获取，时间复杂度是O(n)。
从 Java 8 开始，如果 bucket 中的值包含8个或者更多，则这个 bucket 的数据结构会从列表更改为平衡树，这将性能提升为O(log n)。

### Capacity and Load Factor
为避免多个带有多个值的 bucket，如果75%（负载因子0.75）的 bucket 为非空，则容量增加一倍，负载因子默认0.75，默认初始容量16。两者都可以通过构造函数设置。

### put 和 get 操作
总结一下 put 和 get 的工作方式
- 当我们向 Map 添加元素时， HashMap将计算存储桶。如果存储桶中已经包含一个值，则将该值添加到属于该存储桶的列表（或树）中。如果负载系数大于地图的最大负载系数，则容量将增加一倍。
- 当我们想从 Map 上获取一个值时， HashMap会计算存储桶，并从列表（或树）中使用相同的键来获取值。

## TreeMap
TreeMap实现NavigableMap接口，并将其内部工作基于红黑树的原理：
```java
public class TreeMap<K,V> extends AbstractMap<K,V>
  implements NavigableMap<K,V>, Cloneable, java.io.Serializable
```
- 首先，红黑树是由节点组成的数据结构。规则是，从根开始，任何节点左分支中的任何元素总是小于节点本身中的元素。右边的那些总是更大。大于或小于的定义由元素的自然顺序或在构建时定义的比较器确定，如我们先前所见。
此规则确保树形图的条目将始终按可预测的顺序排序。
- 其次，红黑树是一种自平衡二叉搜索树。此属性及以上内容保证搜索，获取，放置和删除之类的基本操作花费时间 O(log n)。
保持自我平衡是这里的关键。当我们不断插入和删除条目时，可以想象树在一侧长得更长，而在另一侧更短。
这意味着操作在较短的分支上花费的时间较短，而在离根最远的分支上花费的时间更长，这是我们不希望发生的事情。
因此，在设计红黑树时要注意这一点。对于每次插入和删除，树在任何边缘上的最大高度都保持在O（log n），即树不断地自我平衡。

## 如何选择 HashMap、LinkedHashMap 与 TreeMap
- HashMap 是一种通用的 Map 实现，可以提供快速的存储和检索操作。然而，它的不足之处在于它的 entries 混乱无序。
这会导致它在有大量迭代的情况下性能不佳，因为底层数组的整个容量影响遍历，而不仅仅是 entries 数量。
- LinkedHashMap 具有哈希映射的良好属性，并且为 entries 添加了顺序。在大量迭代的情况下，它的性能更好，因为无论容量如何，都只考虑 entries 数量。
- TreeMap 通过提供对键排序方式的完全控制，将排序提升到下一个级别。另一方面，它提供了比其它两种选择更差的总体性能

- 如果要保持条目排序，则应使用 TreeMap
- 如果我们将性能优先于内存消耗，则应使用 HashMap
- 由于 TreeMap 具有更重要的局部性，如果我们要根据对象的自然顺序访问相对靠近的对象，则可以考虑使用它
- 可以使用 initialCapacity 和 loadFactor 调整 HashMap，这对于 TreeMap 是不可能的
- 如果我们想保留插入顺序，同时受益于恒定的访问时间，则可以使用 LinkedHashMap

## ConcurrentMap
ConcurrentMap 是 Map 的扩展。它旨在提供一种结构和指南，以解决吞吐量和线程安全的协调问题。

Map 是 Java 中使用最广泛的样式之一。
重要的是 HashMap 不是线程安全的，而 Hashtable 是通过同步操作来提供线程安全。
即使 Hashtable 是线程安全的，它也不是效率高的。另一个同步的 Map Collections.synchronizedMap 也不具有很高的效率。如果我们想在高并发下实现高吞吐量的线程安全，那么实现这些可不行。
为了解决该问题，Java 1.5 引入了 ConcurrentMap。