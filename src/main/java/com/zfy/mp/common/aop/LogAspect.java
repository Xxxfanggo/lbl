package com.zfy.mp.common.aop;

import com.zfy.mp.common.annotation.Log;
import com.zfy.mp.common.utils.IpUtils;
import com.zfy.mp.common.utils.SecurityUtils;
import com.zfy.mp.common.utils.ServletUtils;
import com.zfy.mp.common.utils.StringUtils;
import com.zfy.mp.domain.entity.LoginUser;
import com.zfy.mp.domain.entity.SysOperLog;
import com.zfy.mp.enums.BusinessStatus;
import com.zfy.mp.mq.producer.log.SendSysLogMQ;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 *
 * @文件名: LogAspect.java
 * @工程名: mp
 * @包名: com.zfy.mp.common.aop
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-30 16:17
 * @版本号: V2.4.0
 */
@Aspect
@Component
public class LogAspect {

    @Resource
    private SendSysLogMQ sendSysLogMQ;

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /** 计算操作消耗时间 */
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new NamedThreadLocal<Long>("Cost Time");

    @Before(value = "@annotation(controllerLog)")
    public void doBefore(JoinPoint joinPoint, Log controllerLog) {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e)
    {
        handleLog(joinPoint, controllerLog, e, null);
    }


    private void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult) {

        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Operation operation = method.getAnnotation(Operation.class);
            // 获取当前的用户
            LoginUser loginUser = SecurityUtils.getLoginUser();

            // *==========数据库日志==========*//

            String ipAddr = IpUtils.getIpAddr();

            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();

            SysOperLog log = SysOperLog.builder()
                    .method(className + "." + methodName + "()")
                    .module(controllerLog.title())
                    .description(operation.summary())
                    .reqMapping(ServletUtils.getRequest().getMethod())
                    .operation(controllerLog.businessType().ordinal())
                    .ip(ipAddr)
                    .reqAddress(ServletUtils.getRequest().getRequestURI())
                    .time(System.currentTimeMillis() - TIME_THREADLOCAL.get())
                    .build();
            if (loginUser != null) {
                log.setUserName(loginUser.getUsername());
            }

            if ( e != null) {
                log.setState(BusinessStatus.FAIL.getCode());
                log.setException(StringUtils.substring(e.getMessage(), 0, 2000));
            } else {
                log.setState(BusinessStatus.SUCCESS.getCode());
            }

            if (jsonResult != null) {
                log.setReturnParameter(StringUtils.substring(jsonResult.toString(), 0, 2000));
            }

            // 保存数据库
            sendSysLogMQ.saveSysLog(log);
        } catch (Exception exp) {
            log.error("异常信息:{} ", e);
            exp.printStackTrace();
        }
        finally {
            TIME_THREADLOCAL.remove();
        }
    }


}
