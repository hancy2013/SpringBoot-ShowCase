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

import com.qbg.springboot.showcase.shiro.dao.ResourceDao;
import com.qbg.springboot.showcase.shiro.entity.Resource;

@Repository
public class ResouceDaoImpl implements ResourceDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Resource create(final Resource resource) {
        final String sql = "insert into sys_resource(name,type,url,permission,parent_id,parent_ids,available) values(?,?,?,?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn)
                    throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql,
                        new String[] { "id" });
                int count = 1;
                ps.setString(count++, resource.getName());
                ps.setString(count++, resource.getType().name());
                ps.setString(count++, resource.getUrl());
                ps.setString(count++, resource.getPermission());
                ps.setLong(count++, resource.getParentId());
                ps.setString(count++, resource.getParentIds());
                ps.setBoolean(count++, resource.getAvailable());
                return ps;
            }
        }, keyHolder);
        resource.setId(keyHolder.getKey().longValue());
        return resource;
    }

    @Override
    public Resource update(Resource resource) {
        final String sql = "update sys_resource set name = ?,type=?,url=?,permission=?,parent_id=?,parent_ids=?,available=? where id = ? ";
        jdbcTemplate.update(sql, resource.getName(), resource.getType().name(),
                resource.getUrl(), resource.getPermission(),
                resource.getParentId(), resource.getParentIds(),
                resource.getAvailable(), resource.getId());
        return resource;
    }

    @Override
    public void delete(Long resourceId) {
        Resource resource = findOne(resourceId);
        final String deleteSelfSql = "delete from sys_resource where id=?";
        jdbcTemplate.update(deleteSelfSql, resourceId);
        final String deleteDescendantsSql = "delete from sys_resource where parent_ids like ?";
        jdbcTemplate.update(deleteDescendantsSql,
                resource.makeSelfAsParentIds() + "%");
    }

    @Override
    public Resource findOne(Long resourceId) {
        final String sql = "select id,name,type,url,permission,parent_id,parent_ids,available from sys_resource where id=?";
        List<Resource> resources = jdbcTemplate
                .query(sql,
                        new BeanPropertyRowMapper<Resource>(Resource.class),
                        resourceId);
        if (resources.size() == 0) {
            return null;
        }
        return resources.get(0);
    }

    @Override
    public List<Resource> findAll() {
        final String sql = "select id,name,type,url,permission,parent_id,parent_ids,available from sys_resource order by concat(parent_ids, id) asc";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Resource>(
                Resource.class));
    }

}
