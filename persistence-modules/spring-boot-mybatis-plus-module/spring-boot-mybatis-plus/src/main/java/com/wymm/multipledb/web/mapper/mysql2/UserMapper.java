package com.wymm.multipledb.web.mapper.mysql2;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wymm.multipledb.web.entity.User;

@DS("mysql2")
public interface UserMapper  extends BaseMapper<User> {

}
