package com.qbg.springboot.showcase.shiro.dao;

import java.util.List;

import com.qbg.springboot.showcase.shiro.entity.Role;

public interface RoleDao {
    
    Role create(Role role);
    
    Role update(Role role);
    
    void delete(Long roleId);
    
    Role findOne(Long roleId);
    
    List<Role> findAll();

}
