package com.zfy.mp.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zfy.mp.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_role")
public class SysRole extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String roleName;
    //角色字符
    private String roleKey;
    // 状态（0：正常，1：停用）
    private Integer status;
    // 顺序
    private Long orderNum;
}
