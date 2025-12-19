package com.zfy.mp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 *
 * @文件名: SysUserRole.java
 * @工程名: mp
 * @包名: com.zfy.mp.domain
 * @描述: 用户角色关联表
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-19 10:27
 * @版本号: V2.4.0
 */
@Data
@TableName("sys_user_role")
public class SysUserRole {
    private String userId;
    private Long roleId;
}
