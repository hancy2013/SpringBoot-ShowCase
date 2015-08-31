package com.qbg.springboot.showcase.shiro.config;

import org.junit.Assert;
import org.junit.Test;

import com.alibaba.druid.filter.config.ConfigTools;

public class DatasourceConfigTest {

    @Test
    public void encryptPassword() throws Exception {
        String password = ConfigTools.encrypt("123456");
        System.out.println(password);
    }

    @Test
    public void decryptPassword() throws Exception {
        String decryptedPassword = "Biyu5YzU+6sxDRbmWEa3B2uUcImzDo0BuXjTlL505+/pTb+/0Oqd3ou1R6J8+9Fy3CYrM18nBDqf6wAaPgUGOg==";
        String password = ConfigTools.decrypt(decryptedPassword);
        Assert.assertEquals("123456", password);
    }
}
