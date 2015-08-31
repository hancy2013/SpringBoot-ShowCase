package com.qbg.springboot.showcase.shiro.service;

import java.util.List;
import java.util.Set;

import com.qbg.springboot.showcase.shiro.entity.User;

public interface UserService {
    
    User create(User user);

    User update(User user);
    
    void delete(Long userId);
    
    User findOne(Long userId);
    
    List<User> findAll();
    
    User findByUsername(String username);
    
    void changePassword(Long userId, String newPassword);
    
    Set<String> findRoles(String username);
    
    Set<String> findPermissions(String username);
}
