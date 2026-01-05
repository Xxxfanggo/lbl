package com.zfy.mp.service.user.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zfy.mp.common.constants.Const;
import com.zfy.mp.common.constants.RespConst;
import com.zfy.mp.common.constants.SecurityConst;
import com.zfy.mp.common.utils.SecurityUtils;
import com.zfy.mp.domain.entity.*;
import com.zfy.mp.enums.RoleEnum;
import com.zfy.mp.mapper.PermissionMapper;
import com.zfy.mp.mapper.RoleMapper;
import com.zfy.mp.mapper.RolePermissionMapper;
import com.zfy.mp.mapper.UserRoleMapper;
import com.zfy.mp.service.user.SysUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @文件名: UserDetailsServiceImpl.java
 * @工程名: mp
 * @包名: com.zfy.mp.service.user.impl
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-05 17:07
 * @版本号: V2.4.0
 */
@Log4j2
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserRoleMapper userRolesMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        HttpServletRequest request = SecurityUtils.getCurrentHttpRequest();

        String equipmentHeader = null;
        String typeHeader = null;
        String accessToken = null;
        if (request != null) {
            equipmentHeader = request.getHeader(Const.TYPE_HEADER);
            typeHeader = request.getHeader(Const.FRONTEND_LOGIN_TYPE);
            accessToken = request.getHeader(Const.FRONTEND_THIRD_LOGIN_TOKEN);
        }

        SysUser sysUser = null;

        if (typeHeader != null) {
            log.info("第三方登录....");
        } else {
            sysUser = sysUserService.findAccountByNameOrEmail( username);
        }
        if (ObjectUtil.isEmpty(sysUser)) {
            // 不存在，抛出异常
            throw new UsernameNotFoundException(RespConst.USERNAME_OR_PASSWORD_ERROR_MSG);
        }

        return handlerLogin(sysUser, equipmentHeader);
    }

    private LoginUser handlerLogin(SysUser sysUser, String equipmentHeader) {

        HttpServletRequest request = SecurityUtils.getCurrentHttpRequest();
        String header = null;
        if (request != null) {
            header = request.getHeader(Const.TYPE_HEADER);
        }

        // 查询角色拥有的权限

        List<SysUserRole> sysUserRoles = userRolesMapper.selectList(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, sysUser.getId()));
        List<SysRole> roles = sysUserRoles.stream().map(role ->
                        roleMapper.selectById(role.getRoleId()))
                .filter(role -> ObjectUtil.equals(role.getStatus(), RoleEnum.ROLE_STATUS_ARTICLE.getStatus()))
                .toList();

        // 用户是否被禁用
        if (sysUser.getIsDisable() == 1) {
            throw new BadCredentialsException(RespConst.ACCOUNT_DISABLED_MSG);
        }

        // 是否测试账号前台
//        if (header == null || (roles.stream().anyMatch(role -> role.getRoleKey().equals(SecurityConst.ROLE_TESTER)) && !header.equals(Const.BACKEND_REQUEST))) {
//            throw new BadCredentialsException(RespConst.TEST_ACCOUNT_MSG);
//        }

        // 判断用户是否具备任何权限,
        if ((equipmentHeader != null && equipmentHeader.equals(Const.BACKEND_REQUEST) && ObjectUtils.isEmpty(roles))) {
            throw new BadCredentialsException(RespConst.NO_PERMISSION_MSG);
        }


        if (!roles.isEmpty()) {
            // 查询权限关系表
            List<SysRolePermission> rolePermissions = rolePermissionMapper.selectBatchIds(roles.stream().map(SysRole::getId).toList());
            // 查询角色权限
            List<Long> pIds = rolePermissions.stream().map(SysRolePermission::getPermissionId).toList();
            List<SysPermission> permissions = permissionMapper.selectBatchIds(pIds);
            // 组合角色，权限
            List<String> list = permissions.stream().map(SysPermission::getPermissionKey).collect(Collectors.toList());
            roles.forEach(role -> list.add(SecurityConst.ROLE_PREFIX + role.getRoleKey()));
            return new LoginUser(sysUser, list);
        }
        return new LoginUser(sysUser, List.of());
    }

}
