package com.zfy.mp.common.handler;

import com.zfy.mp.domain.SysUser;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Copyright © 2025. All rights reserved.万达信息股份有限公司
 *
 * @文件名: UserRoleResultHandler.java
 * @工程名: mp
 * @包名: com.zfy.mp.common.handler
 * @描述: 结果映射处理器
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-19 14:32
 * @版本号: V2.4.0
 */
public class UserRoleResultHandler implements ResultHandler<SysUser> {
    private final Map<String, SysUser> userMap = new HashMap<>();
    @Override
    public void handleResult(ResultContext<? extends SysUser> resultContext) {
        SysUser user = resultContext.getResultObject();
        if (userMap.containsKey(user.getId())) {
            SysUser existingUser = userMap.get(user.getId());
            if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                existingUser.getRoles().addAll(user.getRoles());
            }
        } else {
            userMap.put(user.getId(), user);
            if (user.getRoles() == null ) {
                user.setRoles(new HashSet<>());
            }
        }
    }
}
