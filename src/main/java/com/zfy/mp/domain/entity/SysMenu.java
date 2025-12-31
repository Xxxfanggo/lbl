package com.zfy.mp.domain.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysMenu {
    private Long id;
    private Long parentId;
    private String menuName;
    private String menuCode;
    private Integer menuType;  // 1:目录 2:菜单 3:按钮
    private String icon;
    private String path;
    private String component;
    private String permission;
    private Integer sortOrder;
    private Integer visible;  // 0:隐藏 1:显示
    private Integer status;    // 0:停用 1:正常
    private Integer isFrame;   // 0:外链 1:否
    private Integer isCache;   // 0:缓存 1:不缓存
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    private String remark;

    // 树形结构子节点
    private List<SysMenu> children;

    // 扩展字段
    private String parentName;
    private Boolean hasChildren;
}

