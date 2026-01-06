package com.zfy.mp.service.login;

/**
 *
 * @文件名: OauthLoginService.java
 * @工程名: mp
 * @包名: com.zfy.mp.service.login
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-06 17:20
 * @版本号: V2.4.0
 */
public interface OauthLoginService {
    String handleLogin(String code);
}
