package com.qbg.springboot.showcase.shiro.config;

import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.jni.File;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.qbg.springboot.showcase.Application;
import com.qbg.springboot.showcase.shiro.entity.Role;
import com.qbg.springboot.showcase.shiro.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest
public class RoleServiceTest {

	@Autowired
	RoleService roleService;

	@Test
	@Transactional
	public void create() {
		Role role = new Role();
		role.setRole("r");
		role.setDescription("rt");
		role.setAvailable(true);
		role.setResourceIds(Arrays.asList(1L, 11L));
		role = roleService.create(role);
		Assert.assertNotNull(roleService.findOne(role.getId()));
	}
	
	@Test
	@Transactional
	public void update(){
		Role role = roleService.findOne(2L);
		role.setAvailable(false);
		Assert.assertNotNull(role);
		System.out.println(role);
	}
	
	@Test
	public void findAll(){
		List<Role> roles = roleService.findAll();
		Assert.assertNotNull(roles);
		for (Role role : roles) {
			System.out.println(role);
		}
	}

}
