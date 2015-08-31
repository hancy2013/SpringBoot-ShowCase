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

import com.qbg.springboot.showcase.shiro.dao.RoleDao;
import com.qbg.springboot.showcase.shiro.entity.Role;

@Repository
public class RoleDaoImpl implements RoleDao{
    
    @Autowired
    private JdbcTemplate jdbcTemplate; 

    @Override
    public Role create(final Role role) {
        final String sql = "insert into sys_role(role,description,resource_ids,available) values(?,?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                ps.setString(count++, role.getRole());
                ps.setString(count++, role.getDescription());
                ps.setString(count++, role.getResourceIdsStr());
                ps.setBoolean(count++, role.getAvailable());
                return ps;
            }
        },keyHolder);
        role.setId(keyHolder.getKey().longValue());
        return role;
    }

    @Override
    public Role update(Role role) {
        final String sql = "update sys_role set role=?, description=?, resource_ids=?, available=? where id=?";
        jdbcTemplate.update(sql,role.getRole(),role.getDescription(),role.getResourceIdsStr(),role.getAvailable(),role.getId());
        return role;
    }

    @Override
    public void delete(Long roleId) {
        final String sql = "delete from sys_role where id = ?";
        jdbcTemplate.update(sql,roleId);
    }

    @Override
    public Role findOne(Long roleId) {
        final String sql = "select id, role, description, resource_ids as resourceIdsStr, available from sys_role where id = ?";
        List<Role> roles = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Role>(Role.class),roleId);
        if(roles == null || roles.size()==0){
            return null;
        }
        return roles.get(0);
    }

    @Override
    public List<Role> findAll() {
        final String sql = "select id, role, description, resource_ids as resourceIdsStr, available from sys_role";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Role>(Role.class));
    }

}
