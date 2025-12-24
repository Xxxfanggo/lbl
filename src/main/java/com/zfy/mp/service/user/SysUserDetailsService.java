package com.zfy.mp.service.user;

import com.zfy.mp.domain.SysPermissions;
import com.zfy.mp.domain.SysRole;
import com.zfy.mp.domain.SysUser;
import com.zfy.mp.mapper.SysPermissionsMapper;
import com.zfy.mp.mapper.SysUserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @文件名: CustomUserDetailsService.java
 * @工程名: mp
 * @包名: com.zfy.mp.service
 * @描述: 自定义 UserDetailsService
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-19 10:32
 * @版本号: V2.4.0
 */
@Service
public class SysUserDetailsService implements UserDetailsService {
    /**
     *  CustomUserDetailsService ：提供查询用户信息，如 根据用户名查询用户，并返回UserDetails
     *  UserDetails ：SpringSecurity定义的类 封装了用户信息，如用户名，密码，权限等
     */

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysPermissionsMapper  sysPermissionsMapper;

    @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.findByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // username 唯一
        SysUserDetails sysUserDetails = new SysUserDetails(sysUser);

        Set<SysRole> roles = sysUser.getRoles();
        sysUserDetails.setRoles(roles);
        // 查询角色拥有的权限
        Set<Long> roleIds = roles.stream().map(SysRole::getId).collect(Collectors.toSet());
        List<SysPermissions> permissionList = sysPermissionsMapper.getRolePermissionsByRoleIds(roleIds);
        Set<String> permissions = permissionList.stream().map(SysPermissions::getPermissionsName).collect(Collectors.toSet());
        sysUserDetails.setPermissions(permissions);
        return sysUserDetails;

    }

}
