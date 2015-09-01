package com.qbg.springboot.showcase.shiro.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.qbg.springboot.showcase.Application;
import com.qbg.springboot.showcase.shiro.entity.User;
import com.qbg.springboot.showcase.shiro.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=Application.class)
@IntegrationTest
public class UserServiceTest {
	
	@Autowired
	UserService userService;
	
	@Test
	@Transactional
	public void create(){
		User user = new User();
		user.setUsername("tt");
		user.setPassword("pwd");
		user.setSalt("asdafdadf");
		user.setLocked(false);
		user = userService.create(user);
		Assert.assertNotNull(userService.findOne(user.getId()));
	}
	
	@Test
	@Transactional
	public void update(){
		User user = userService.findByUsername("t");
		Assert.assertNotNull(user);
		System.out.println(user);
	}

}
