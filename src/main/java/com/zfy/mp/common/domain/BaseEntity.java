package com.zfy.mp.common.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @文件名: BaseEntity.java
 * @工程名: mp
 * @包名: com.zfy.mp.common.domain
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-05 14:10
 * @版本号: V2.4.0
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
    /** 删除标识 */
    private Integer isDeleted;
    /** 创建者 */
    private String createBy;
    /** 备注 */
    private String remark;
}
