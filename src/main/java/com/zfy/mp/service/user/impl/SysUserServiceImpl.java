package com.zfy.mp.service.user.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zfy.mp.common.constants.RedisConst;
import com.zfy.mp.common.constants.RespConst;
import com.zfy.mp.common.constants.UserConst;
import com.zfy.mp.common.utils.JWTUtil;
import com.zfy.mp.common.utils.RedisCache;
import com.zfy.mp.domain.dto.UserRegisterDTO;
import com.zfy.mp.domain.entity.LoginUser;
import com.zfy.mp.domain.entity.SysUser;
import com.zfy.mp.domain.response.ResponseResult;
import com.zfy.mp.domain.vo.LoginUserVO;
import com.zfy.mp.enums.RegisterOrLoginTypeEnum;
import com.zfy.mp.enums.RespEnum;
import com.zfy.mp.mapper.UserMapper;
import com.zfy.mp.service.user.SysUserService;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 *
 * @文件名: SysUserServiceImpl.java
 * @工程名: mp
 * @包名: com.zfy.mp.service.user.impl
 * @描述: todo
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-19 15:28
 * @版本号: V2.4.0
 */
@Log4j2
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements SysUserService  {

    @Override
    public SysUser findAccountByNameOrEmail(String text) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, text).or().eq(SysUser::getEmail, text).eq(SysUser::getRegisterType, RegisterOrLoginTypeEnum.EMAIL.getStrategy());
        return baseMapper.selectOne(wrapper);
    }

}
