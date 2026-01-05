package com.zfy.mp.domain.entity;

import cn.hutool.core.util.ObjectUtil;
import com.zfy.mp.common.utils.RedisCache;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 *
 * @文件名: LoginUser.java
 * @工程名: mp
 * @包名: com.zfy.mp.domain.entity
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-05 15:02
 * @版本号: V2.4.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LoginUser implements UserDetails {

    @Resource
    private RedisCache redisCache;

    private SysUser sysUser;
    private List<String> permissions;
    //存储SpringSecurity所需要的权限信息的集合
    private List<SimpleGrantedAuthority> authorities;

    public LoginUser(SysUser user) {
        this.sysUser = user;
    }

    public LoginUser(SysUser user, List<String> permissions) {
        // 登陆时调用构造函数，传递 permissions
        this.sysUser = user;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (Objects.nonNull( authorities)) return authorities;
        authorities = permissions.stream().map(SimpleGrantedAuthority::new).toList();
        return authorities;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    /**
     * 账号是否过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用户是否锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户凭证是否过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否可用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
