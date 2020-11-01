package com.wymm.guava;

import com.google.common.cache.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class _1GuavaCacheTest {
    
    /**
     * 使用CacheLoader加载缓存
     */
    @Test
    public void useCacheLoaderLoadCache() throws ExecutionException {
        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public String load(String s) throws Exception {
                return s.toUpperCase();
            }
        };
        
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().build(loader);
        
        assertEquals(0, loadingCache.size());
        assertEquals("HELLO", loadingCache.get("hello"));
        assertEquals(1, loadingCache.size());
    }
    
    /**
     * 使用Callable加载缓存
     */
    @Test
    public void useCallableLoadCache() throws ExecutionException {
        Cache<Object, Object> loadingCache = CacheBuilder.newBuilder().build();
        Object cacheValue = loadingCache.get("hello", (Callable<String>) () -> "HELLO");
        
        assertEquals("HELLO", cacheValue);
        assertEquals(1, loadingCache.size());
    }
    
    /**
     * 使用CacheLoader加载缓存，如果没有就从Callable获取默认值
     */
    @Test
    public void useCacheLoaderAndCallableLoadLoadCache() throws ExecutionException {
        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public String load(String s) throws Exception {
                if (s.contains("Callable")) {
                    return null;
                }
                return s.toUpperCase();
            }
        };
        
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().build(loader);
        
        assertEquals(0, loadingCache.size());
        assertEquals("HELLO", loadingCache.get("hello"));
        assertEquals(1, loadingCache.size());
        
        Object cacheValue = loadingCache.get("callable", (Callable<String>) () -> "CALLABLE");
        assertEquals("CALLABLE", cacheValue);
    }
    
    /**
     * 使用maximumSize()限制缓存的大小
     * 到达限制最早的数据还被逐出缓存
     */
    @Test
    public void whenCacheReachMaxSize_thenEviction() throws ExecutionException {
        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public String load(String s) throws Exception {
                return s.toUpperCase();
            }
        };
        
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .build(loader);
        
        loadingCache.get("first");
        loadingCache.get("second");
        loadingCache.get("third");
        loadingCache.get("forth");
        assertEquals(3, loadingCache.size());
        assertNull(loadingCache.getIfPresent("first"));
        assertEquals("FORTH", loadingCache.get("forth"));
    }
    
    /**
     * 使用自定义权重函数(Weigher)来限制缓存大小
     * 下面使用长度作为自定义权重函数
     * 缓存可能会删除多个记录，为新的大记录让出位置
     */
    @Test
    public void whenCacheReachMaxSizeWeight_thenEviction() throws ExecutionException {
        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public String load(String s) throws Exception {
                return s.toUpperCase();
            }
        };
        
        Weigher<String, String> weighByLength = (key, value) -> value.length();
        
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                .maximumWeight(16).weigher(weighByLength)
                .build(loader);
        
        loadingCache.get("first");
        loadingCache.get("second");
        loadingCache.get("third");
        // length:16
        loadingCache.get("forth");
        assertEquals(3, loadingCache.size());
        assertNull(loadingCache.getIfPresent("first"));
        assertEquals("FORTH", loadingCache.get("forth"));
    }
    
    /**
     * 按缓存闲置时间来驱逐缓存
     */
    @Test
    public void whenEntryIdle_thenEviction() throws ExecutionException, InterruptedException {
        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public String load(String s) throws Exception {
                return s.toUpperCase();
            }
        };
        
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                .expireAfterAccess(200, TimeUnit.MICROSECONDS)
                .build(loader);
        
        loadingCache.get("hello");
        assertEquals(1, loadingCache.size());
        
        Thread.sleep(300);
        loadingCache.get("word");
        assertEquals(1, loadingCache.size());
        assertNull(loadingCache.getIfPresent("first"));
    }
    
    /**
     * 按缓存生存时间来驱逐缓存
     */
    @Test
    public void whenEntryLiveTimeExpire_thenEviction() throws ExecutionException, InterruptedException {
        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public String load(String s) throws Exception {
                return s.toUpperCase();
            }
        };
        
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(200, TimeUnit.MICROSECONDS)
                .build(loader);
        
        loadingCache.get("hello");
        assertEquals(1, loadingCache.size());
        Thread.sleep(300);
        loadingCache.get("word");
        assertEquals(1, loadingCache.size());
        assertNull(loadingCache.getIfPresent("first"));
    }
    
    
    /**
     * 使用weakKeys()设置键弱引用，当键被回收驱逐缓存
     */
    @Test
    public void whenWeakKeyHasNoRef_thenEviction() throws InterruptedException {
        Cache<Object, Object> loadingCache = CacheBuilder.newBuilder()
                .weakKeys()
                .build();
        
        Object key = new Object();
        loadingCache.put(key, "hello");
        key = new Object();
        System.gc();
        assertNull(loadingCache.getIfPresent(key));
    }
    
    /**
     * 使用weakValues()设置值弱引用，当值被回收驱逐缓存
     */
    @Test
    public void whenWeakValueHasNoRef_thenEviction() throws InterruptedException {
        Cache<Object, Object> loadingCache = CacheBuilder.newBuilder()
                .weakValues()
                .build();
        
        Object value = new Object();
        loadingCache.put("hello", value);
        value = new Object();
        System.gc();
        Thread.sleep(1000);
        assertNull(loadingCache.getIfPresent("hello"));
    }
    
    /**
     * 使用softValues()设置值软引用，当值被回收驱逐缓存
     */
    @Test
    public void whenSoftValueHasNoRef_thenEviction() throws InterruptedException {
        Cache<Object, Object> loadingCache = CacheBuilder.newBuilder()
                .softValues()
                .build();
    }
    
    /**
     * 利用Optional处理空值，避免异常
     */
    @Test
    public void whenNullValue_thenOptional() throws ExecutionException {
        Function<String, String> getSuffix = (str) -> {
            int i = str.lastIndexOf(".");
            if (i == -1) {
                return null;
            }
            return str.substring(i + 1);
        };
        
        CacheLoader<String, Optional<String>> loader = new CacheLoader<String, Optional<String>>() {
            @Override
            public Optional<String> load(String key) throws Exception {
                return Optional.ofNullable(getSuffix.apply(key));
            }
        };
        
        LoadingCache<String, Optional<String>> loadingCache = CacheBuilder.newBuilder().build(loader);
        
        assertEquals("txt", loadingCache.get("java.txt").get());
        assertFalse(loadingCache.get("java").isPresent());
    }
    
    /**
     * 使用refreshAfterWrite()自动刷新缓存
     * 注意：缓存项只有被检索时才会真正刷新，缓存项也会在过期后变得可以回收
     * 也可以使用refresh()刷新特定的缓存
     */
    @Test
    public void whenLiveTimeEnd_thenRefresh() throws ExecutionException, InterruptedException {
        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public String load(String s) throws Exception {
                return s.toUpperCase();
            }
        };
        
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                .refreshAfterWrite(1, TimeUnit.SECONDS)
                .build(loader);
    }
    
    /**
     * putAll()预加载缓存
     */
    @Test
    public void whenPreloadCache_thenUsePutAll() {
        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public String load(String s) throws Exception {
                return s.toUpperCase();
            }
        };
        
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().build(loader);
        
        Map<String, String> map = new HashMap<>();
        map.put("first", "FIRST");
        map.put("second", "SECOND");
        
        loadingCache.putAll(map);
        
        assertEquals(2, loadingCache.size());
    }
    
    /**
     * 缓存移除通知
     */
    @Test
    public void whenEntryRemoveFromCache_thenNotify() {
        CacheLoader<String, String> cacheLoader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                return key.toUpperCase();
            }
        };
        
        RemovalListener<String, String> listener = new RemovalListener<String, String>() {
            @Override
            public void onRemoval(RemovalNotification<String, String> notification) {
                if (notification.wasEvicted()) {
                    String cause = notification.getCause().name();
                    assertEquals(RemovalCause.SIZE.toString(), cause);
                }
            }
        };
        
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .removalListener(listener)
                .build(cacheLoader);
        
        loadingCache.getUnchecked("first");
        loadingCache.getUnchecked("second");
        loadingCache.getUnchecked("third");
        loadingCache.getUnchecked("last");
        
        assertEquals(3, loadingCache.size());
        
    }
    
    /**
     * 读取 DB 模板：
     * 缓存30秒无访问移除缓存
     * 缓存3分钟后异步刷新值
     * 缓存从数据库获取
     * 初始化加载所有缓存
     */
    void template() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(new DriverManagerDataSource("jdbc:mysql://localhost:3306", "root", "root"));
        
        final String FIND_INFO = "select * from TEMPLATE_TABLE";
        final String FIND_INFO_BY_ID_SQL = FIND_INFO + " where ID = ?";
        final LoadingCache<String, Optional<Map<String, Object>>> loadingCache = CacheBuilder.newBuilder()
                .refreshAfterWrite(3, TimeUnit.MINUTES)
                .expireAfterAccess(30, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Optional<Map<String, Object>>>() {
                    @Override
                    public Optional<Map<String, Object>> load(String id) {
                        try {
                            return Optional.of(jdbcTemplate.queryForMap(FIND_INFO_BY_ID_SQL, id));
                        } catch (EmptyResultDataAccessException e) {
                            log.error("未知编号:" + id);
                            return Optional.empty();
                        } catch (IncorrectResultSizeDataAccessException e) {
                            log.error("多个编号异常:" + id);
                            return Optional.empty();
                        }
                    }
                });
        loadingCache.putAll(jdbcTemplate.queryForList(FIND_INFO)
                .stream()
                .collect(Collectors.toMap(map -> map.get("ID").toString(), Optional::of)));
        
    }
}
