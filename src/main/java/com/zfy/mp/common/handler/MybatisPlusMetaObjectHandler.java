package com.zfy.mp.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *
 * @文件名: MetaObjectHandler.java
 * @工程名: mp
 * @包名: com.zfy.mp.common.handler
 * @描述: mybatis-plus自动填充处理器
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-13 10:20
 * @版本号: V2.4.0
 */
@Log4j2
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    @Override
     public void insertFill(MetaObject metaObject) {
         log.info("start insert fill ....");
          this.setFieldValByName("createTime", new Date(), metaObject);
           this.setFieldValByName("updateTime", new Date(), metaObject);
    }
     @Override
      public void updateFill(MetaObject metaObject) {
         log.info("start update fill ....");
         this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
