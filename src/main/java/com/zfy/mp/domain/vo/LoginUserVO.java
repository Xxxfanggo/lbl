package com.zfy.mp.domain.vo;

import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

/**
 * 
 *
 * @文件名: loginUserVO.java
 * @工程名: mp
 * @包名: com.zfy.mp.vo.user
 * @描述: 登陆
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-15 14:09
 * @版本号: V2.4.0
 */
@Data
public class LoginUserVO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}
