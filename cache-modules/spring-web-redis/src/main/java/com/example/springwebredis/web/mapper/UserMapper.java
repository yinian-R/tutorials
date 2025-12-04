package com.example.springwebredis.web.mapper;

import com.example.springwebredis.web.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user order by id limit 0,50")
    List<User> findUsers();
    
    @Select("select * from user where id=#{id}")
    User getUser(@Param("id") Long id);
    
    @Insert("insert into user(name) values(#{user.name})")
    boolean addUser(@Param("user") User user);
    
    @Delete("delete from user where id = #{id}")
    boolean deleteUser(@Param("id") Long id);
    
    @Update("update user set name = #{user.name} where id = #{user.id}")
    boolean updateUser(@Param("user") User user);
}
