package com.zfy.mp.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_permissions")
public class SysPermissions {
    @TableId(type = IdType.AUTO)
    private String id;
    private String permissionsName;
}
