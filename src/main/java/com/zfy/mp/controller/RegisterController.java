package com.zfy.mp.controller;

import com.zfy.mp.domain.dto.UserRegisterDTO;
import com.zfy.mp.domain.response.ResponseResult;
import com.zfy.mp.service.register.RegisterService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @文件名: RegisterController.java
 * @工程名: mp
 * @包名: com.zfy.mp.controller
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-08 16:48
 * @版本号: V2.4.0
 */
@RestController
public class RegisterController {
    @Resource
    private RegisterService registerService;
    @PostMapping("/register")
    public ResponseResult<Void> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        return registerService.userRegister(userRegisterDTO);
    }
}
