package com.zfy.mp.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zfy.mp.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_permission")
public class SysPermission extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String permissionsDesc;
    //权限字符
    private String permissionKey;
    // 菜单id
    private Long menuId;
}
