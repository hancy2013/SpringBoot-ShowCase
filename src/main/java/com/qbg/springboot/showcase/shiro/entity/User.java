package com.qbg.springboot.showcase.shiro.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Data
@EqualsAndHashCode(of = { "id" })
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String salt;
    private List<Long> roleIds;
    private Boolean locked = Boolean.FALSE;

    public List<Long> getRoleIds() {
        if (roleIds == null) {
            roleIds = new ArrayList<Long>();
        }
        return roleIds;
    }

    public String getRoleIdsStr() {
        if (CollectionUtils.isEmpty(roleIds)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Long roleId : roleIds) {
            sb.append(roleId);
            sb.append(",");
        }
        return sb.toString();
    }

    public void setRoleIdsStr(String roleIdsStr) {
        if (StringUtils.isEmpty(roleIdsStr)) {
            return;
        }
        String[] roleIdStrs = roleIdsStr.split(",");
        for (String roleIdStr : roleIdStrs) {
            if (StringUtils.isEmpty(roleIdStr)) {
                continue;
            }
            getRoleIds().add(Long.valueOf(roleIdStr));
        }
    }

    public String getCredentialsSalt() {
        return username + salt;
    }
}
