package com.zfy.mp.service.publIc;

/**
 *
 * @文件名: publicService.java
 * @工程名: mp
 * @包名: com.zfy.mp.service.publIc
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-08 15:54
 * @版本号: V2.4.0
 */
public interface PublicService {

    String registerEmailVerifyCode(String email, String type);
}
