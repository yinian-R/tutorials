package com.wymm.collections;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class _8ConcurrentMapTest {
    
    /**
     * 多线程使用 HashMap
     * 让我们看一下不一致的情况
     */
    @Test
    void givenHashMap_whenSumParallel_thenError() {
        Map<String, Integer> map = new HashMap<>();
        List<Integer> sumList = parallelSum100(map, 100);
        
        assertNotEquals(1, sumList
                .stream()
                .distinct()
                .count());
        long wrongResultCount = sumList
                .stream()
                .filter(num -> num != 100)
                .count();
        
        assertTrue(wrongResultCount > 0);
    }
    
    private List<Integer> parallelSum100(Map<String, Integer> map, int executionTimes) {
        List<Integer> sumList = new ArrayList<>(1000);
        for (int i = 0; i < executionTimes; i++) {
            map.put("test", 0);
            ExecutorService executorService = Executors.newFixedThreadPool(4);
            for (int j = 0; j < 10; j++) {
                executorService.execute(() -> {
                    for (int k = 0; k < 10; k++) {
                        map.computeIfPresent("test", (key, value) -> value + 1);
                    }
                });
            }
            executorService.shutdown();
            sumList.add(map.get("test"));
        }
        return sumList;
    }
    
    /**
     * 多线程使用 ConcurrentHashMap
     * 可以获得一致且正确的结果
     */
    @Test
    void givenConcurrentMap_whenSumParallel_thenCorrect() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        List<Integer> sumList = parallelSum100(map, 1000);
        
        assertEquals(1, sumList
                .stream()
                .distinct()
                .count());
        long wrongResultCount = sumList
                .stream()
                .filter(num -> num != 100)
                .count();
        
        assertEquals(0, wrongResultCount);
    }
    
    /**
     * ConcurrentHashMap 在大多数并发数据检索和更新的情况下应该会产生更好的性能。
     * 让我们为 get 和 put 性能编写一个快速的微基准测试，并将其与 Hashtable 和 Map.synchronizedMap，在4个线程中运行这两个操作500000次
     * 性能高到低：ConcurrentHashMap > Hashtable > Map.synchronizedMap
     * 请记住，微基准测试仅针对单个场景，并不总是能很好地反映现实情况。
     */
    @Test
    void givenMaps_whenGetPut500KTimes_thenConcurrentMapFaster()
            throws Exception {
        Map<String, Object> hashtable = new Hashtable<>();
        Map<String, Object> synchronizedHashMap = Collections.synchronizedMap(new HashMap<>());
        Map<String, Object> concurrentHashMap = new ConcurrentHashMap<>();
        
        long hashtableAvgRuntime = timeElapseForGetPut(hashtable);
        long syncHashMapAvgRuntime = timeElapseForGetPut(synchronizedHashMap);
        long concurrentHashMapAvgRuntime = timeElapseForGetPut(concurrentHashMap);
        
        log.debug("Hashtable:" + hashtableAvgRuntime);
        log.debug("synchronized HashMap:" + syncHashMapAvgRuntime);
        log.debug("ConcurrentHashMap:" + concurrentHashMapAvgRuntime);
        
        assertTrue(hashtableAvgRuntime > concurrentHashMapAvgRuntime);
        assertTrue(syncHashMapAvgRuntime > concurrentHashMapAvgRuntime);
    }
    
    private long timeElapseForGetPut(Map<String, Object> map) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        long startTime = System.nanoTime();
        for (int i = 0; i < 4; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 500_000; j++) {
                    int value = ThreadLocalRandom
                            .current()
                            .nextInt(10000);
                    String key = String.valueOf(value);
                    map.put(key, value);
                    map.get(key);
                }
            });
        }
        executorService.shutdown();
        return (System.nanoTime() - startTime) / 500_000;
    }
    
    /**
     * 多线程使用 ConcurrentSkipListMap
     * 之前，我们介绍了 NavigableMap 接口及其实现 TreeMap。可以看到 ConcurrentSkipListMap 是 TreeMap 的可伸缩并发版本。
     */
    @Test
    void givenSkipListMap_whenNavConcurrently_thenCountCorrect() throws InterruptedException {
        NavigableMap<Integer, Integer> skipListMap = new ConcurrentSkipListMap<>();
        int count = countMapElementByPollingFirstEntry(skipListMap, 10000, 4);
        
        assertEquals(10000 * 4, count);
    }
    
    /**
     * 多线程使用 TreeMap
     * 让我们看一下不一致的情况
     */
    @Test
    public void givenTreeMap_whenNavConcurrently_thenCountError()
            throws InterruptedException {
        NavigableMap<Integer, Integer> treeMap = new TreeMap<>();
        int count = countMapElementByPollingFirstEntry(treeMap, 10000, 4);
        
        assertNotEquals(10000 * 4, count);
    }
    
    private int countMapElementByPollingFirstEntry(
            NavigableMap<Integer, Integer> navigableMap,
            int elementCount,
            int concurrencyLevel) throws InterruptedException {
        
        for (int i = 0; i < elementCount * concurrencyLevel; i++) {
            navigableMap.put(i, i);
        }
        
        AtomicInteger counter = new AtomicInteger(0);
        ExecutorService executorService
                = Executors.newFixedThreadPool(concurrencyLevel);
        for (int j = 0; j < concurrencyLevel; j++) {
            executorService.execute(() -> {
                for (int i = 0; i < elementCount; i++) {
                    if (navigableMap.pollFirstEntry() != null) {
                        counter.incrementAndGet();
                    }
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        return counter.get();
    }
}
