package com.example.springwebredis.web.mapper;

import com.example.springwebredis.web.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user order by id limit 0,50")
    List<User> findUsers();
    
    @Select("select * from user where id=#{id}")
    User getUser(@Param("id") String id);
    
    @Insert("insert into user(name) values(#{user.name})")
    boolean addUser(@Param("user") User user);
    
    @Delete("delete from user where id = #{id}")
    boolean deleteUser(@Param("id") String id);
}
