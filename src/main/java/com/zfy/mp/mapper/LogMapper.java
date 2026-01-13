package com.zfy.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zfy.mp.domain.entity.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @文件名: LogMapper.java
 * @工程名: mp
 * @包名: com.zfy.mp.mapper
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-13 16:28
 * @版本号: V2.4.0
 */
@Mapper
public interface LogMapper extends BaseMapper<SysOperLog> {
}
