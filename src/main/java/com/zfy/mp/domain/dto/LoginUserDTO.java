package com.zfy.mp.domain.dto;

import lombok.Data;

/**
 *
 * @文件名: LoginUserDTO.java
 * @工程名: mp
 * @包名: com.zfy.mp.domain.dto
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-31 15:19
 * @版本号: V2.4.0
 */
@Data
public class LoginUserDTO {
    private Long id;
    private String username;
    private String token;
}
