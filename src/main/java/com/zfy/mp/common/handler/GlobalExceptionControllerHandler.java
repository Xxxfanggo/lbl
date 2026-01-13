package com.zfy.mp.common.handler;

import com.zfy.mp.domain.response.ResponseResult;
import com.zfy.mp.enums.RespEnum;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Objects;

/**
 *
 * @文件名: GlobalExceptionControllerHandler.java
 * @工程名: mp
 * @包名: com.zfy.mp.common.handler
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-08 11:13
 * @版本号: V2.4.0
 */
@Log4j2
@RestControllerAdvice
public class GlobalExceptionControllerHandler {

    /**
     * 统一处理参数校验异常
     * @param e 异常
     * @return 响应结果
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult<Void> handlerConstraintDeclarationException(ConstraintViolationException e) {
        System.out.println("参数校验异常:" + e.getMessage());
        log.error("参数校验异常:{}({})", e.getMessage(), e.getStackTrace());
        return ResponseResult.failure(RespEnum.PARAM_ERROR.getCode(), e.getMessage().split(":")[1]);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<Void> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("参数校验异常:{}({})", e.getMessage(), e.getStackTrace());
        BindingResult bindingResult = e.getBindingResult();
        return ResponseResult.failure(RespEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseResult<Void> handlerHandlerMethodValidationException(HandlerMethodValidationException e){
        log.error("参数校验异常:{}({})", e.getMessage(), e.getStackTrace());
        return ResponseResult.failure(RespEnum.PARAM_ERROR.getCode(),e.getValueResults().get(0).getResolvableErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseResult<Void> handlerFileUploadException(FileUploadException e){
        log.error("文件上传异常:{}({})", e.getMessage(), e.getStackTrace());
        String bindingResult = e.getMessage();
        return ResponseResult.failure(RespEnum.FILE_UPLOAD_ERROR.getCode(), bindingResult);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseResult<Void> handleAuthenticationException(AuthenticationException ex) {
        log.error("认证异常: {}({})", ex.getMessage(), ex.getStackTrace());
        return ResponseResult.failure(RespEnum.NOT_LOGIN.getCode(), "认证失败：" + ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult<Void> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("访问拒绝异常: {}({})", ex.getMessage(), ex.getStackTrace());
        return ResponseResult.failure(RespEnum.NO_PERMISSION.getCode(), "权限不足：" + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult<Void> handleAllExceptions(Exception ex) {
        log.error("==== 进入全局异常处理器 [Exception.class] ====");
        log.error("未处理的异常类型: {}", ex.getClass().getName());
        log.error("异常信息: {}", ex.getMessage());
        log.error("堆栈跟踪:", ex);

        return ResponseResult.failure(500, "系统内部错误: " + ex.getClass().getSimpleName());
    }




//    @ExceptionHandler(BlackListException.class)
//    public ResponseResult<Void> handlerBlackListException(BlackListException e){
//        log.error("黑名单异常:{}({})", e.getMessage(), e.getStackTrace());
//        return ResponseResult.failure(RespEnum.BLACK_LIST_ERROR.getCode(), e.getMessage());
//    }

}
