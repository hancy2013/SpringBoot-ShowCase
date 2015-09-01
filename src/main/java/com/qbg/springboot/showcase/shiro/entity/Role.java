package com.qbg.springboot.showcase.shiro.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(of={"id"})
@RequiredArgsConstructor
public class Role implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String role; //角色标识，用于基于角色的判断
    private String description; //角色描述，界面显示用
    private List<Long> resourceIds;
    private Boolean available = Boolean.FALSE;
    
    public List<Long> getResourceIds(){
        if(resourceIds == null){
            resourceIds = new ArrayList<Long>();
        }
        return resourceIds;
    }
    
    public String getResourceIdsStr(){
        if(CollectionUtils.isEmpty(resourceIds)){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Long resourceId : resourceIds) {
            sb.append(resourceId);
            sb.append(",");
        }
        return sb.toString();
    }
    
    public void setResourceIdsStr(String resourceIdsStr){
        if(StringUtils.isEmpty(resourceIdsStr)){
            return;
        }
        String[] resourceIdStrs = resourceIdsStr.split(",");
        for (String resourceIdStr : resourceIdStrs) {
            if(StringUtils.isEmpty(resourceIdStr)){
                continue;
            }
            getResourceIds().add(Long.valueOf(resourceIdStr));
        }
    }

}
