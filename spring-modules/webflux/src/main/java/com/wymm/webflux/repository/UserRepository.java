package com.wymm.webflux.repository;

import com.wymm.webflux.domain.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    /**
     * 根据年龄查找用户
     *
     * @param start 开始年龄(不包含)
     * @param end   结束年龄(不包含)
     * @return 用户列表
     */
    Flux<User> findByAgeBetween(int start, int end);
    
    /**
     * 得到20~30年龄的用户
     *
     * @return 20~30年龄的用户
     */
    @Query("{'age' : {'$gte' : 20, '$lte' : 30}}")
    Flux<User> oldUser();
    
    
}
