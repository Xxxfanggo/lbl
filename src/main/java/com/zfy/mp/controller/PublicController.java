package com.zfy.mp.controller;

import com.zfy.mp.domain.response.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    private final SecurityFilterChain securityFilterChain;

    public PublicController(SecurityFilterChain securityFilterChain) {
        this.securityFilterChain = securityFilterChain;
    }

    @Autowired
    private ApplicationContext applicationContext;
    @GetMapping("/ask-code")
    public ResponseResult<String> askVerifyCode(
            @RequestParam @Email String email,
            @RequestParam @Pattern(regexp = "(register|reset|resetEmail)") String type,
            HttpServletRequest request
    ) throws  Exception{
        StringBuilder sb = new StringBuilder();
        sb.append("=== Security Filter Chain ===\n");

        securityFilterChain.getFilters().forEach(filter ->
                sb.append(filter.getClass().getSimpleName()).append("\n")
        );

        sb.append("\n=== Request Matchers ===\n");
        sb.append("Request URI: ").append(request.getRequestURI()).append("\n");
        sb.append("Context Path: ").append(request.getContextPath()).append("\n");
        System.out.println(sb);
        List<String> collect = Arrays.stream(applicationContext.getBeanNamesForType(HandlerExceptionResolver.class))
                .collect(Collectors.toList());
        System.out.println(collect);

        throw new Exception("测试异常");

//        return ResponseResult.success(sb.toString());
    }
}
