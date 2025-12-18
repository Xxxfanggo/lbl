package com.zfy.mp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
    private String id;
    private String username;
    private String password;
}
