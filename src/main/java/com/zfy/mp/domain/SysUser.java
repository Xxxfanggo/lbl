package com.zfy.mp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Set;

/**
 *
 * @文件名: SysUser.java
 * @工程名: mp
 * @包名: com.zfy.mp.domain
 * @描述: 用户实体类
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-17 10:30
 * @版本号: V2.4.0
 */
@Data
@TableName("sys_user")
public class SysUser {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String username;
    private String password;
    @TableField(exist = false)
    private Set<SysRole> roles;
}
