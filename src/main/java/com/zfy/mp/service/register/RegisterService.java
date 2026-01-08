package com.zfy.mp.service.register;

import com.zfy.mp.domain.dto.UserRegisterDTO;
import com.zfy.mp.domain.response.ResponseResult;

/**
 *
 * @文件名: RegisterService.java
 * @工程名: mp
 * @包名: com.zfy.mp.service.register
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-08 16:35
 * @版本号: V2.4.0
 */
public interface RegisterService {
    ResponseResult<Void> userRegister(UserRegisterDTO userRegisterDTO);
}
