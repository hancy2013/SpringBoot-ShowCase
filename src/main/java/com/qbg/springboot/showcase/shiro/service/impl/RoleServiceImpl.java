package com.qbg.springboot.showcase.shiro.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.qbg.springboot.showcase.shiro.dao.RoleDao;
import com.qbg.springboot.showcase.shiro.entity.Role;
import com.qbg.springboot.showcase.shiro.service.ResourceService;
import com.qbg.springboot.showcase.shiro.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
    
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ResourceService resourceService;

    @Override
    public Role create(Role role) {
        return roleDao.create(role);
    }

    @Override
    public Role update(Role role) {
        return roleDao.update(role);
    }

    @Override
    public void delete(Long roleId) {
        roleDao.delete(roleId);
    }

    @Override
    public Role findOne(Long roleId) {
        return roleDao.findOne(roleId);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Set<String> findRoles(Long... roleIds) {
        if(roleIds == null || roleIds.length == 0){
            return Collections.emptySet();
        }
        Set<String> roles = new HashSet<String>();
        for (Long roleId : roleIds) {
            Role role = findOne(roleId);
            if(role == null || StringUtils.isEmpty(role.getRole())){
                continue;
            }
            roles.add(role.getRole());
        }
        return roles;
    }

    /**
     * @param roleIds
     * @return
     */
    @Override
    public Set<String> findPermissions(Long[] roleIds) {
        if(roleIds == null || roleIds.length == 0){
            return Collections.emptySet();
        }
        Set<Long> resourceIds = new HashSet<Long>();
        for(Long roleId : roleIds){
            Role role = findOne(roleId);
            if(role != null){
                resourceIds.addAll(role.getResourceIds());
            }
        }
        return resourceService.findPermissions(resourceIds);
    }

}
