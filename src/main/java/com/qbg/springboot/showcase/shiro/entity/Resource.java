package com.qbg.springboot.showcase.shiro.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of={"id"})
public class Resource implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id; //编号
    private String name; //资源名称
    private String url; //资源路径
    private String permission; //权限字符串
    private Long parentId; //父编号
    private String parentIds; //父编号列表
    private Boolean available = Boolean.FALSE;
    private ResourceType type = ResourceType.menu;//资源类型
    
    public static enum ResourceType{
        
        menu("菜单"),button("按钮");
        
        final String info;
        
        private ResourceType(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }
    
    public String makeSelfAsParentIds(){
        return getParentIds()+getParentId()+"/";
    }
    
    public boolean isRootNode(){
        return parentId == 0;
    }
    
}
