package com.zfy.mp.service.register.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zfy.mp.common.constants.RedisConst;
import com.zfy.mp.common.constants.RespConst;
import com.zfy.mp.common.constants.UserConst;
import com.zfy.mp.common.utils.RedisCache;
import com.zfy.mp.domain.dto.UserRegisterDTO;
import com.zfy.mp.domain.entity.SysUser;
import com.zfy.mp.domain.response.ResponseResult;
import com.zfy.mp.enums.RegisterOrLoginTypeEnum;
import com.zfy.mp.enums.RespEnum;
import com.zfy.mp.mapper.UserMapper;
import com.zfy.mp.service.register.RegisterService;
import com.zfy.mp.service.user.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 * @文件名: RegisterServiceImpl.java
 * @工程名: mp
 * @包名: com.zfy.mp.service.register.impl
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-08 16:36
 * @版本号: V2.4.0
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Resource
    private RedisCache redisCache;
    @Resource
    private UserMapper userMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private SysUserService sysUserService;

    @Override
    public ResponseResult<Void> userRegister(UserRegisterDTO userRegisterDTO) {
        //1. 验证code
        ResponseResult<Void> verifyCode = verifyCode(userRegisterDTO.getEmail(), userRegisterDTO.getCode(), RedisConst.REGISTER);
        if (verifyCode != null)
            return verifyCode;
        //2. 判断用户名或邮箱是否存在
        if (userIsExist(userRegisterDTO.getUsername(), userRegisterDTO.getEmail())) {
            return ResponseResult.failure(RespEnum.USERNAME_OR_EMAIL_EXIST.getCode(), RespEnum.USERNAME_OR_EMAIL_EXIST.getMsg());
        }
        //4. 密码加密
        String enPassword = passwordEncoder.encode(userRegisterDTO.getPassword());
        Date now = new Date();

        //5. todo 获取注册ip地址
//        String ipAddr = IpUtils.getIpAddr(SecurityUtils.getCurrentHttpRequest());
//        if (IpUtils.isUnknown(ipAddr)) {
//            ipAddr = IpUtils.getHostIp();
//        }

        //6. 保存用户信息
        SysUser sysUser = SysUser.builder()
                .nickname(userRegisterDTO.getUsername())
                .username(userRegisterDTO.getUsername())
                .password(enPassword)
                .registerType(RegisterOrLoginTypeEnum.EMAIL.getRegisterType())
                .email(userRegisterDTO.getEmail())
                .gender(UserConst.DEFAULT_GENDER)
                .avatar(UserConst.DEFAULT_AVATAR)
//                .intro(UserConst.DEFAULT_INTRODUCTION)
//                .loginType(RegisterOrLoginTypeEnum.EMAIL.getStrategy())
                .loginIp("127.0.0.1")
                .loginTime(now)
                .build();

        if (sysUserService.save(sysUser)) {
            // todo
//            ipService.refreshIpDetailAsyncByUidAndRegister(user.getId());
            redisCache.deleteObject(RedisConst.VERIFY_CODE + RedisConst.REGISTER + RedisConst.SEPARATOR + userRegisterDTO.getEmail());
            return ResponseResult.success();
        } else {
            return ResponseResult.failure();
        }
    }



    private ResponseResult<Void> verifyCode(String email, String code, String type) {
        String redisCode = redisCache.getCacheObject(RedisConst.VERIFY_CODE + type + RedisConst.SEPARATOR + email);

        if (StrUtil.isEmpty(redisCode))
            return ResponseResult.failure(RespEnum.VERIFY_CODE_ERROR.getCode(), RespConst.VERIFY_CODE_NULL_MSG);
        if (!redisCode.equals(code))
            return ResponseResult.failure(RespEnum.VERIFY_CODE_ERROR.getCode(), RespEnum.VERIFY_CODE_ERROR.getMsg());
        return null;
    }

    /**
     * 判断用户名或邮箱是否已存在
     *
     * @param username 用户名
     * @param email    邮箱
     * @return boolean
     */
    private boolean userIsExist(String username, String email) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username).or().eq(SysUser::getEmail, email);
        return this.userMapper.selectOne(wrapper) != null;
    }


}
