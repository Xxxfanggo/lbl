package com.zfy.mp.service.user;

import com.zfy.mp.domain.SysUser;
import com.zfy.mp.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
    private UserMapper userMapper;

    @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userMapper.findUserWithRolesByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // username 唯一
        SysUserDetails sysUserDetails = new SysUserDetails(sysUser);
        return sysUserDetails;
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        sysUser.getRoles().forEach(role ->
//                authorities.add((GrantedAuthority) () -> role.getRoleName())
//        );
//        return  org.springframework.security.core.userdetails.User.builder()
//                .username(sysUser.getUsername())
//                .password(sysUser.getPassword())
//                .authorities(sysUser.getRoles().stream().map(sysRole -> new SimpleGrantedAuthority(sysRole.getRoleName())).collect(Collectors.toSet()))
//                .build();
    }

}
