package com.zfy.mp.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * Copyright © 2026. All rights reserved.万达信息股份有限公司
 *
 * @文件名: RoleEnum.java
 * @工程名: mp
 * @包名: com.zfy.mp.enums
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-05 15:38
 * @版本号: V2.4.0
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {
    ROLE_STATUS_ARTICLE(0, "状态：正常"),
    ROLE_STATUS_UNARTICLE(1, "状态：停用");

    // 类型
    private final Integer status;
    // 描述
    private final String desc;
}
