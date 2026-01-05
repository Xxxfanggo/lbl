package com.zfy.mp.service.login;

import com.zfy.mp.domain.vo.LoginUserVO;

/**
 *
 * @文件名: LoginService.java
 * @工程名: mp
 * @包名: com.zfy.mp.service.login
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-05 17:22
 * @版本号: V2.4.0
 */
public interface LoginService {
    String login(LoginUserVO user);
}
