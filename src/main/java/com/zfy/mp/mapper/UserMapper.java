package com.zfy.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zfy.mp.domain.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * @文件名: UserMapper.java
 * @工程名: mp
 * @包名: com.zfy.mp.mapper
 * @描述: userMapper
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-17 10:31
 * @版本号: V2.4.0
 */
public interface UserMapper extends BaseMapper<SysUser> {
    @Select("SELECT u.*, r.id as role_id, r.name as role_name " +
            "FROM sys_user u " +
            "LEFT JOIN sys_user_role ur ON u.id = ur.user_id " +
            "LEFT JOIN sys_role r ON ur.role_id = r.id " +
            "WHERE u.username = #{username} limit 0, 1")
    SysUser findUserWithRolesByUsername(@Param("username") String username);
}
