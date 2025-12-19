package com.zfy.mp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 *
 * @文件名: SysRole.java
 * @工程名: mp
 * @包名: com.zfy.mp.domain
 * @描述: 角色实体类
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-16 15:56
 * @版本号: V2.4.0
 */
@Data
@TableName("sys_role")
public class SysRole {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String roleName;
}
