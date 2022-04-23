package com.wymm.stream;

import com.wymm.stream._2grouping.BlogPost;
import com.wymm.stream._2grouping.BlogPostType;
import com.wymm.stream._2grouping.Tuple;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.maxBy;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 了解 groupingBy 收集器
 * <p>
 * 我们使用 groupingBy 按某些属性对对象进行分组并将结果存储在Map实例中
 * <p>
 * groupingBy 的重载方法有：
 * 首先，以分类函数作为方法参数：
 * static <T,K> Collector<T,?,Map<K,List<T>>>
 * groupingBy(Function<? super T,? extends K> classifier)
 * <p>
 * 其次，以分类函数和第二个收集器作为方法参数：
 * static <T,K,A,D> Collector<T,?,Map<K,D>>
 * groupingBy(Function<? super T,? extends K> classifier,
 * Collector<? super T,A,D> downstream)
 * <p>
 * 最后，使用分类函数、供应商方法（提供包含最终结果的Map实现）和第二个收集器作为方法参数：
 * static <T,K,D,A,M extends Map<K,D>> Collector<T,?,M>
 * groupingBy(Function<? super T,? extends K> classifier,
 * Supplier<M> mapFactory, Collector<? super T,A,D> downstream)
 */
public class _2GroupingByTest {
    private static final List<BlogPost> posts = Arrays.asList(
            new BlogPost("News item 1", "Author 1", BlogPostType.NEWS, 15),
            new BlogPost("Tech review 1", "Author 2", BlogPostType.REVIEW, 5),
            new BlogPost("Programming guide", "Author 1", BlogPostType.GUIDE, 20),
            new BlogPost("News item 2", "Author 2", BlogPostType.NEWS, 35),
            new BlogPost("Tech review 2", "Author 1", BlogPostType.REVIEW, 15));
    
    /**
     * 按单列简单分组
     */
    @Test
    void givenAListOfPosts_whenGroupedByType_thenGetAMapBetweenTypeAndPosts() {
        Map<BlogPostType, List<BlogPost>> postsPerType = posts.stream()
                .collect(groupingBy(BlogPost::getType));
        
        assertEquals(2, postsPerType.get(BlogPostType.NEWS)
                .size());
        assertEquals(1, postsPerType.get(BlogPostType.GUIDE)
                .size());
        assertEquals(2, postsPerType.get(BlogPostType.REVIEW)
                .size());
    }
    
    /**
     * 具有复杂映射键类型的groupingBy
     *
     * 分类函数不限于只返回一个标量或字符串值。只要我们确保实现了必要的equals和hashcode方法，生成的映射的键可以是任何对象。
     */
    @Test
    public void givenAListOfPosts_whenGroupedByComplexMapKeyType_thenGetAMapBetweenTupleAndList() {
        Map<Tuple, List<BlogPost>> postsPerTypeAndAuthor = posts.stream()
                .collect(groupingBy(post -> new Tuple(post.getType(), post.getAuthor())));
        
        List<BlogPost> result = postsPerTypeAndAuthor.get(new Tuple(BlogPostType.GUIDE, "Author 1"));
        
        assertEquals(result.size(), 1);
    }
    
    /**
     * 修改返回的映射值类型
     *
     * groupingBy的第二个重载采用附加的第二个收集器（下游收集器），该收集器应用于第一个收集器的结果。
     * 当我们指定分类函数，但不指定下游收集器时，在后台使用toList()收集器。
     */
    @Test
    public void givenAListOfPosts_whenGroupedByTypeInSets_thenGetAMapBetweenTypesAndSetsOfPosts() {
        Map<BlogPostType, Set<BlogPost>> postsPerType = posts.stream()
                .collect(groupingBy(BlogPost::getType, toSet()));
        
        assertEquals(2, postsPerType.get(BlogPostType.NEWS)
                .size());
        assertEquals(1, postsPerType.get(BlogPostType.GUIDE)
                .size());
        assertEquals(2, postsPerType.get(BlogPostType.REVIEW)
                .size());
    }
    
    /**
     * 按多个字段分组
     *
     * 下游收集器的另一个应用是对第一个 group by 的结果进行二次groupingBy 。
     */
    @Test
    public void givenAListOfPosts_whenGroupedByAuthorAndThenByType_thenGetAMapBetweenAuthorAndMapsBetweenTypeAndBlogPosts() {
        Map<String, Map<BlogPostType, List<BlogPost>>> map = posts.stream()
                .collect(groupingBy(BlogPost::getAuthor, groupingBy(BlogPost::getType)));
        
        assertEquals(1, map.get("Author 1")
                .get(BlogPostType.NEWS)
                .size());
        assertEquals(1, map.get("Author 1")
                .get(BlogPostType.GUIDE)
                .size());
        assertEquals(1, map.get("Author 1")
                .get(BlogPostType.REVIEW)
                .size());
        
        assertEquals(1, map.get("Author 2")
                .get(BlogPostType.NEWS)
                .size());
        assertEquals(1, map.get("Author 2")
                .get(BlogPostType.REVIEW)
                .size());
        assertNull(map.get("Author 2")
                .get(BlogPostType.GUIDE));
    }
    
    /**
     * 从分组结果中获取平均值
     * 我还可以使用 summingInt、summarizingInt等聚合函数
     *
     * 通过使用下游收集器，我们可以在分类函数的结果中应用聚合函数。
     */
    @Test
    public void givenAListOfPosts_whenGroupedByTypeAndAveragingLikes_thenGetAMapBetweenTypeAndAverageNumberOfLikes() {
        Map<BlogPostType, Double> averageLikesPerType = posts.stream()
                .collect(groupingBy(BlogPost::getType, averagingInt(BlogPost::getLikes)));
        
        assertEquals(25, averageLikesPerType.get(BlogPostType.NEWS)
                .intValue());
        assertEquals(20, averageLikesPerType.get(BlogPostType.GUIDE)
                .intValue());
        assertEquals(10, averageLikesPerType.get(BlogPostType.REVIEW)
                .intValue());
    }
    
    /**
     * 将分组结果映射到不同类型
     *
     * 我们可以通过将下游收集器映射(mapping)到分类函数的结果来实现更复杂的聚合。
     */
    @Test
    public void givenAListOfPosts_whenGroupedByTypeAndTheirTitlesAreJoinedInAString_thenGetAMapBetweenTypeAndCsvTitles() {
        Map<BlogPostType, String> postsPerType = posts.stream()
                .collect(groupingBy(BlogPost::getType, mapping(BlogPost::getTitle, joining(", ", "Post titles: [", "]"))));
        
        assertEquals("Post titles: [News item 1, News item 2]", postsPerType.get(BlogPostType.NEWS));
        assertEquals("Post titles: [Programming guide]", postsPerType.get(BlogPostType.GUIDE));
        assertEquals("Post titles: [Tech review 1, Tech review 2]", postsPerType.get(BlogPostType.REVIEW));
    }
    
    /**
     * 修改返回映射类型
     *
     * 在使用groupingBy收集器时，我们不能对返回的Map的类型做出假设。如果我们想具体说明我们想从 group by 中获取哪种类型的Map，
     * 那么我们可以使用groupingBy方法的第三种变体，它允许我们通过传递Map供应商函数来更改Map的类型。
     */
    @Test
    public void givenAListOfPosts_whenGroupedByTypeInAnEnumMap_thenGetAnEnumMapBetweenTypeAndPosts() {
        EnumMap<BlogPostType, List<BlogPost>> postsPerType = posts.stream()
                .collect(groupingBy(BlogPost::getType, () -> new EnumMap<>(BlogPostType.class), toList()));
        
        assertEquals(2, postsPerType.get(BlogPostType.NEWS)
                .size());
        assertEquals(1, postsPerType.get(BlogPostType.GUIDE)
                .size());
        assertEquals(2, postsPerType.get(BlogPostType.REVIEW)
                .size());
    }
    
    
    /**
     * 与groupingBy类似的是groupingByConcurrent收集器，它利用了多核架构。
     * 此收集器具有三个重载方法，它们采用与groupingBy收集器的相应重载方法完全相同的参数。
     * 然而，groupingByConcurrent收集器的返回类型必须是ConcurrentHashMap类或其子类的实例。
     *
     * 要同时进行分组操作，流需要是并行的
     */
    @Test
    public void givenAListOfPosts_whenGroupedByTypeConcurrently_thenGetAMapBetweenTypeAndPosts() {
        // 必须并行流
        ConcurrentMap<BlogPostType, List<BlogPost>> postsPerType = posts.parallelStream()
                .collect(groupingByConcurrent(BlogPost::getType));
        
        assertEquals(2, postsPerType.get(BlogPostType.NEWS)
                .size());
        assertEquals(1, postsPerType.get(BlogPostType.GUIDE)
                .size());
        assertEquals(2, postsPerType.get(BlogPostType.REVIEW)
                .size());
    }
    
    
}
