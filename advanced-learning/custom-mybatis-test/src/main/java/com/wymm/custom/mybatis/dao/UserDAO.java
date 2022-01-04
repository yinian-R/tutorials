package com.wymm.custom.mybatis.dao;

import com.wymm.custom.mybatis.entity.User;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.util.List;

/**
 * 用户信息持久层
 */
public interface UserDAO {
    
    List<User> finds() throws Exception;
    
    User findByName(User user) throws Exception;
}
