package com.zfy.mp.controller;

import com.zfy.mp.domain.entity.SysUser;
import com.zfy.mp.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @文件名: UserController.java
 * @工程名: mp
 * @包名: com.zfy.mp.controller
 * @描述: 用户相关控制器
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-17 10:32
 * @版本号: V2.4.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserMapper userMapper;
    @GetMapping("/getAllUser")
    public List<SysUser> getUserInfo() {
        List<SysUser> userList = userMapper.selectList(null);
        for (SysUser user : userList) {
//            System.out.println("用户名：" + user.getUsername());
        }
        return userList;
    }
}
