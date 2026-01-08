package com.zfy.mp.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @文件名: UserRegisterDTO.java
 * @工程名: mp
 * @包名: com.zfy.mp.domain.dto
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-08 16:06
 * @版本号: V2.4.0
 */
@Data
public class UserRegisterDTO {
    // 用户名
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$")
    @Length(min = 1, max = 10)
    private String username;
    // 密码
    @Length(min = 6, max = 20)
    private String password;
    //验证码
    @Length(max = 6, min = 6)
    private String code;
    // 邮箱
    @Email
    @Length(min = 4)
    private String email;
}
