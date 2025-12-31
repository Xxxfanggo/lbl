package com.zfy.mp.service.user;

import com.zfy.mp.domain.entity.SysRole;
import com.zfy.mp.domain.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Copyright © 2025. All rights reserved.万达信息股份有限公司
 *
 * @文件名: SysUserDetails.java
 * @工程名: mp
 * @包名: com.zfy.mp.service.user
 * @描述: 实现UserDetails
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-19 15:07
 * @版本号: V2.4.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserDetails implements UserDetails {
    private String id;
    private String username;
    private String password;
    private Set<SysRole> roles;
    private Set<String> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("进入权限的获取方法");

        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> {
            String roleName = role.getRoleName();
            if (!roleName.startsWith("ROLE_")) {
                roleName = "ROLE_" + roleName;
            }
            authorities.add(new SimpleGrantedAuthority(roleName));
        });
        permissions.forEach(permission -> {
            authorities.add(new SimpleGrantedAuthority(permission));
        });

        System.out.println("最终权限列表: " + authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList());

        return authorities;
    }

    public SysUserDetails(SysUser sysUser) {
        this.id = sysUser.getId();
        this.username = sysUser.getUsername();
        this.password = sysUser.getPassword();
    }

    //    后面四个方法都是用户是否可用、是否过期之类的。我都设置为true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
