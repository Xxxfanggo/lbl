package com.zfy.mp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @文件名: BusinessStatus.java
 * @工程名: mp
 * @包名: com.zfy.mp.enums
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-13 16:01
 * @版本号: V2.4.0
 */
@Getter
@AllArgsConstructor
public enum BusinessStatus {
    /**
     * 成功
     */
    SUCCESS(0, "成功"),

    /**
     * 失败
     */
    FAIL(1, "失败"),
    /**
     * 异常
     */
    EXCEPTION(2, "异常");

    private Integer code;
    private String message;


}
