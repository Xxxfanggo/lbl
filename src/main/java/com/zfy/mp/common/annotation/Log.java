package com.zfy.mp.common.annotation;

import com.zfy.mp.enums.BusinessType;
import com.zfy.mp.enums.OperatorType;

import java.lang.annotation.*;

/**
 *
 * @文件名: Log.java
 * @工程名: mp
 * @包名: com.zfy.mp.common.annonation
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-30 16:12
 * @版本号: V2.4.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    public boolean isSaveResponseData() default true;

    /**
     * 排除指定的请求参数
     */
    public String[] excludeParamNames() default {};
}
