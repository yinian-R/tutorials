package com.wymm.multipledb.web.repository.mysql;

import com.wymm.multipledb.web.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    
    @Select("select * from user")
    List<User> finds();
    
}
