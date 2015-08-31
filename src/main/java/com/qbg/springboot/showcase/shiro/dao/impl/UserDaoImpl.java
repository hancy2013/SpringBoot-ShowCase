package com.qbg.springboot.showcase.shiro.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.qbg.springboot.showcase.shiro.dao.UserDao;
import com.qbg.springboot.showcase.shiro.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public User create(final User user) {
        final String sql = "insert into sys_user(username,password,salt,role_ids,locked) values(?,?,?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                ps.setString(count++, user.getUsername());
                ps.setString(count++, user.getPassword());
                ps.setString(count++, user.getSalt());
                ps.setString(count++, user.getRoleIdsStr());
                ps.setBoolean(count++, user.getLocked());
                return ps;
            }
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    @Override
    public User update(User user) {
        final String sql = "update sys_user set username=?, password=?, salt=?, role_ids=?,locked=? where id=?";
        jdbcTemplate.update(sql, user.getUsername(),user.getPassword(),user.getSalt(),user.getRoleIdsStr(),user.getLocked(),user.getId());
        return user;
    }

    @Override
    public void delete(Long userId) {
        final String sql = "delete from sys_user where id=?";
        jdbcTemplate.update(sql,userId);
    }

    @Override
    public User findOne(Long userId) {
        String sql = "select id, username, password, salt, role_ids as roleIdsStr, locked from sys_user where id = ?";
       List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class),userId);
       if(users==null || users.size()==0){
        return null;
       }
       return users.get(0);
    }

    @Override
    public List<User> findAll() {
        String sql = "select id, username, password, salt, role_ids as roleIdsStr, locked from sys_user where id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
    }

    @Override
    public User findByUsername(String username) {
        String sql = "select id, username, password, salt, role_ids as roleIdsStr, locked from sys_user where username = ?";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class),username);
        if(users==null || users.size()==0){
         return null;
        }
        return users.get(0);
    }

}
