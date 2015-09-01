package com.qbg.springboot.showcase.shiro.config;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qbg.springboot.showcase.Application;
import com.qbg.springboot.showcase.shiro.entity.Resource;
import com.qbg.springboot.showcase.shiro.entity.Resource.ResourceType;
import com.qbg.springboot.showcase.shiro.service.ResourceService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest
public class ResourceServiceTest {

    @Autowired
    ResourceService resourceService;

    @Test
    public void create() {
        Resource r = new Resource();
        r.setName("t");
        r.setParentId(0L);
        r.setPermission("*");
        r.setType(ResourceType.menu);
        r.setUrl("/");
        r.setAvailable(true);
        r = resourceService.create(r);
        Assert.assertNotNull(r);
        Assert.assertNotNull(resourceService.findOne(r.getId()));
    }
    
    @Test
    public void update(){
    	Resource resource = resourceService.findOne(46L);
    	resource.setAvailable(false);
    	Resource resource2 = resourceService.update(resource);
    	Assert.assertEquals(false, resource2.getAvailable());
    }
    
    @Test
    public void findAll(){
    	List<Resource> resources = resourceService.findAll();
    	Assert.assertNotNull(resources);
    	for (Resource resource : resources) {
			System.out.println(resource);
		}
    }
    
}
