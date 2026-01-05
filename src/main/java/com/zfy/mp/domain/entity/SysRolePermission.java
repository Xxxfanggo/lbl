package com.zfy.mp.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @文件名: SysRolePermission.java
 * @工程名: mp
 * @包名: com.zfy.mp.domain.entity
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-05 14:40
 * @版本号: V2.4.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_role_permission")
public class SysRolePermission {
    //关系表id
    @TableId(type = IdType.AUTO)
    private Integer id;
    //角色id
    private Long roleId;
    //权限id
    private Long permissionId;
}
