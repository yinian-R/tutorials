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

## HashMap

HashMap 的有点是插入和检索的时间复杂度都是O(1)

HashMap 将元素存储在所谓的 buckets 中，buckets 的数量称为 Capacity。
当我们往map中add一个值的时候，键的hashCode()方法用于确定存储在其中的buckets。
为了检索该值，HashMap使用相同的方式（hashCOde()）计算 buckets。然后遍历在该 bucket 中找到对象，并使用建的equals()方法找到完全匹配的对象。

我们应该使用不可变的键，因为一旦键改变了，我们将不能获取相应的值，而是返回null

### 碰撞
为了使键能正常工作，相同的键必须具有相同的哈希，但是不同的键也可以具有相同的哈希。
如果两个不同的键具有相同的哈希，则属于他们的值存储在同一个 bucket 中。
在 bucket 中，值存储在列表中，并通过遍历列表来获取，时间复杂度是O(n)。

从 Java 8 开始，如果 bucket 中的值包含8个或者更多，则这个 bucket 的数据结构会从列表更改为平衡树，这将性能提升为O(log n)

### Capacity and Load Factor
为避免多个带有多个值的 bucket，如果75%（负载因子0.75）的 bucket 为非空，则容量增加一倍，负载因子默认0.75，默认初始容量16。两者都可以通过构造函数设置。

### put 和 get 操作
总结一下 put 和 get 的工作方式
- 当我们向 Map 添加元素时， HashMap将计算存储桶。如果存储桶中已经包含一个值，则将该值添加到属于该存储桶的列表（或树）中。如果负载系数大于地图的最大负载系数，则容量将增加一倍。
- 当我们想从 Map 上获取一个值时， HashMap会计算存储桶，并从列表（或树）中使用相同的键来获取值。