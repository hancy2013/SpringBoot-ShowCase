package com.qbg.springboot.showcase.shiro.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.qbg.springboot.showcase.shiro.dao.ResourceDao;
import com.qbg.springboot.showcase.shiro.entity.Resource;
import com.qbg.springboot.showcase.shiro.entity.Resource.ResourceType;
import com.qbg.springboot.showcase.shiro.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;
    
    @Override
    public Resource create(Resource resource) {
        return resourceDao.create(resource);
    }

    @Override
    public Resource update(Resource resource) {
        return resourceDao.update(resource);
    }

    @Override
    public void delete(Long resourceId) {
        resourceDao.delete(resourceId);
    }

    @Override
    public Resource findOne(Long resourceId) {
        return resourceDao.findOne(resourceId);
    }

    @Override
    public List<Resource> findAll() {
        return resourceDao.findAll();
    }

    @Override
    public Set<String> findPermissions(Set<Long> resourceIds) {
        Set<String> permissions = new HashSet<String>();
        for (Long resourceId : resourceIds) {
            Resource resource = findOne(resourceId);
            if(resource !=null && !StringUtils.isEmpty(resource.getPermission())){
                permissions.add(resource.getPermission());
            }
        }
        return permissions;
    }

    @Override
    public List<Resource> findMenus(Set<String> permissions) {
        List<Resource> allResources = resourceDao.findAll();
        List<Resource> menus = new ArrayList<Resource>();
        for(Resource resource : allResources){
            if(resource.isRootNode()){
                continue;
            }
            if(resource.getType() != ResourceType.menu){
                continue;
            }
            if(!hasPermission(permissions,resource)){
                continue;
            }
            menus.add(resource);
        }
        return menus;
    }

    private boolean hasPermission(Set<String> permissions, Resource resource) {
        if(StringUtils.isEmpty(resource.getPermission())){
            return true;
        }
        WildcardPermission p2 = new WildcardPermission(resource.getPermission());
        for (String permission : permissions) {
           WildcardPermission p1 = new WildcardPermission(permission);
           if(p1.implies(p2) || p2.implies(p1)){
               return true;
           }
        }
        return false;
    }

}
