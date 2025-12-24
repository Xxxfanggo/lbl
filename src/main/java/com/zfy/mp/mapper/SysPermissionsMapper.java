package com.zfy.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zfy.mp.domain.SysPermissions;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface SysPermissionsMapper extends BaseMapper<SysPermissions> {
    @Select("<script>" +
            "SELECT p.* FROM sys_permissions p " +
            "LEFT JOIN sys_role_permission srp ON p.id = srp.permissions_id " +
            "LEFT JOIN sys_role sr ON srp.role_id = sr.id " +
            "WHERE sr.id IN " +
            "<foreach collection='roleIds' item='roleId' open='(' separator=',' close=')'>" +
            "#{roleId}" +
            "</foreach>" +
            "</script>")
    List<SysPermissions> getRolePermissionsByRoleIds(@Param("roleIds") Set<Long> roleIds);
}
