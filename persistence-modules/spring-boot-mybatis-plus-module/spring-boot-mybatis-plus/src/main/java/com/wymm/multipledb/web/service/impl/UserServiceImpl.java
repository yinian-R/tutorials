package com.wymm.multipledb.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wymm.multipledb.web.entity.User;
import com.wymm.multipledb.web.mapper.mysql2.UserMapper;
import com.wymm.multipledb.web.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    //
    //@Override
    //public List<User> searchUser(List<SearchCriteria> params) {
    //
    //    Example example = new Example(User.class);
    //    UserSearchQueryCriteriaConsumer searchConsumer = new UserSearchQueryCriteriaConsumer(example);
    //    params.forEach(searchConsumer);
    //
    //    return userMapper.selectByExample(example);
    //}
    
}
