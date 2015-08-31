package com.qbg.springboot.showcase.shiro.service;

import java.util.List;
import java.util.Set;

import com.qbg.springboot.showcase.shiro.entity.Resource;

public interface ResourceService {
    
    Resource create(Resource resource);
    
    Resource update(Resource resource);
    
    void delete(Long resourceId);
    
    Resource findOne(Long resourceId);
    
    List<Resource> findAll();
    
    Set<String> findPermissions(Set<Long> resourceIds);
    
    List<Resource> findMenus(Set<String> permissions);
 
}
