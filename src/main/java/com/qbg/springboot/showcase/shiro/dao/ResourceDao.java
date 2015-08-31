package com.qbg.springboot.showcase.shiro.dao;

import java.util.List;

import com.qbg.springboot.showcase.shiro.entity.Resource;

public interface ResourceDao {
    
    Resource create(Resource resource);
    
    Resource update(Resource resource);
    
    void delete(Long resourceId);
    
    Resource findOne(Long resourceId);
    
    List<Resource> findAll();

}
