package com.qbg.springboot.showcase.shiro.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qbg.springboot.showcase.shiro.dao.UserDao;
import com.qbg.springboot.showcase.shiro.entity.User;
import com.qbg.springboot.showcase.shiro.service.RoleService;
import com.qbg.springboot.showcase.shiro.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    public User create(User user) {
        passwordHelper.encryptPassword(user);
        return userDao.create(user);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public void delete(Long userId) {
        userDao.delete(userId);
    }

    @Override
    public User findOne(Long userId) {
        return userDao.findOne(userId);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        User user = userDao.findOne(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userDao.update(user);
    }

    @Override
    public Set<String> findRoles(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return Collections.emptySet();
        }
        return roleService.findPermissions(user.getRoleIds().toArray(
                new Long[0]));
    }

    @Override
    public Set<String> findPermissions(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return Collections.emptySet();
        }
        return roleService.findPermissions(user.getRoleIds().toArray(
                new Long[0]));
    }

}
