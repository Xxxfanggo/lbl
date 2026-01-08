package com.zfy.mp.controller;

import com.zfy.mp.domain.response.ResponseResult;
import com.zfy.mp.service.publIc.PublicService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @文件名: PublicController.java
 * @工程名: mp
 * @包名: com.zfy.mp.controller
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-08 10:21
 * @版本号: V2.4.0
 */
@RestController
@RequestMapping("/public")
public class PublicController {
    @Resource
    private PublicService publicService;
    @GetMapping("/ask-code")
    public ResponseResult<String> askVerifyCode(
            @RequestParam @Email String email,
            @RequestParam @Pattern(regexp = "(register|reset|resetEmail)") String type
    ) {
        String verifyCode = publicService.registerEmailVerifyCode(email, type);

        return ResponseResult.success(verifyCode);
    }
}
