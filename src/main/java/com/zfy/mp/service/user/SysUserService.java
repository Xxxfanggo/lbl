package com.zfy.mp.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zfy.mp.domain.dto.UserRegisterDTO;
import com.zfy.mp.domain.entity.SysUser;
import com.zfy.mp.domain.response.ResponseResult;

/**
 *
 * @文件名: SysUserService.java
 * @工程名: mp
 * @包名: com.zfy.mp.service.user
 * @描述: todo
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-19 15:24
 * @版本号: V2.4.0
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名或者密码查询用户
     *
     * @param text 用户名或者邮箱
     * @return 用户信息
     */
    SysUser findAccountByNameOrEmail(String text);

}
