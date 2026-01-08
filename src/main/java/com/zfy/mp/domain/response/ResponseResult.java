package com.zfy.mp.domain.response;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zfy.mp.enums.RespEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @文件名: ResponseReslut.java
 * @工程名: mp
 * @包名: com.zfy.mp.domain.response
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-08 10:23
 * @版本号: V2.4.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(RespEnum.SUCCESS.getCode(), RespEnum.SUCCESS.getMsg(), null);
    }
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(RespEnum.SUCCESS.getCode(), RespEnum.SUCCESS.getMsg(), data);
    }
    public static <T> ResponseResult<T> success(String message, T data) {
        return new ResponseResult<>(RespEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> ResponseResult<T> failure(Integer code, String msg) {
        return new ResponseResult<>(code, msg, null);
    }

    public static <T> ResponseResult<T> failure() {
        return new ResponseResult<>(RespEnum.FAILURE.getCode(), RespEnum.FAILURE.getMsg(), null);
    }
    public static <T> ResponseResult<T> failure(String msg) {
        return new ResponseResult<>(RespEnum.FAILURE.getCode(), msg, null);
    }
    public static <T> ResponseResult<T> failure(T data)  {
        return new ResponseResult<>(RespEnum.FAILURE.getCode(), RespEnum.FAILURE.getMsg(), data);
    }

    /**
     * 转json字符串
     *
     * @return {@link String}
     */
    public String asJsonString() {
        return JSONObject.toJSONString(this, JSONObject.DEFAULT_GENERATE_FEATURE);
    }
}
