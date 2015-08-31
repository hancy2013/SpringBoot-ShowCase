package com.qbg.springboot.showcase.shiro.dao;

import java.util.List;

import com.qbg.springboot.showcase.shiro.entity.User;

public interface UserDao {
    
    User create(User user);
    
    User update(User user);
    
    void delete(Long userId);
    
    User findOne(Long userId);
    
    List<User> findAll();
    
    User findByUsername(String username);

}
