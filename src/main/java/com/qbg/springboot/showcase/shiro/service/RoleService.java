package com.qbg.springboot.showcase.shiro.service;

import java.util.List;
import java.util.Set;

import com.qbg.springboot.showcase.shiro.entity.Role;

public interface RoleService {
    
    Role create(Role role);
    
    Role update(Role role);
    
    void delete(Long roleId);
    
    Role findOne(Long roleId);
    
    List<Role> findAll();
    
    Set<String> findRoles(Long...roleIds);
    
    Set<String> findPermissions(Long[] roleIds);
}
