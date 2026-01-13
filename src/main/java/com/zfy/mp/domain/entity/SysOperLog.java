package com.zfy.mp.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.zfy.mp.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Date;

/**
 *
 * @文件名: SysOperLog.java
 * @工程名: mp
 * @包名: com.zfy.mp.domain.entity
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-13 14:37
 * @版本号: V2.4.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_log")
public class SysOperLog extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    //编号
    @TableId(type = IdType.AUTO)
    private Long id;
    //模块名称
    private String module;
    //操作
    private Integer operation;
    //操作人员
    private String userName;
    //ip地址
    private String ip;
    //操作地点
    private String address;
    //操作状态(0：成功，1：失败，2：异常)
    private Integer state;
    //操作方法
    private String method;
    // 请求方式
    private String reqMapping;
    //请求参数
    private String reqParameter;
    // 异常信息
    private String exception;
    //返回参数
    private String returnParameter;
    //请求地址
    private String reqAddress;
    //消耗时间(ms)
    private Long time;
    // 操作描述
    private String description;

}
