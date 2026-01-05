package com.zfy.mp.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zfy.mp.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_user")
public class SysUser extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String nickname;
    private String username;
    private String password;
    // 性别（0:未知 1:男 2:女）
    private Integer gender;
    private String avatar;
    private String email;
    //用户注册方式(0邮箱/姓名 1Gitee 2Github)
    private Integer registerType;
    // 用户注册地址
    private String registerAddress;
    //用户登录方式(0邮箱/姓名 1Gitee 2Github)
    private Integer loginType;
    // 用户登录ip
    private String loginIp;
    // 登录地址
    private String loginAddress;
    //是否禁用 0 否 1 是
    private Integer isDisable;
    //用户最近登录时间
    private Date loginTime;
    @TableField(exist = false)
    private Set<SysRole> roles;
}
