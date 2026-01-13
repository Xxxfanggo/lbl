package com.zfy.mp.common.utils;

import com.zfy.mp.common.exception.ServiceException;
import com.zfy.mp.domain.entity.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @文件名: SecurityUtils.java
 * @工程名: mp
 * @包名: com.zfy.mp.common.utils
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-31 14:08
 * @版本号: V2.4.0
 */
public class SecurityUtils {


    /**
     * 获取request
     * @return request
     */
    public static HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return requestAttributes.getRequest();
        }
        return null;
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        try
        {
            return (LoginUser) getAuthentication().getPrincipal();
        }
        catch (Exception e)
        {
            throw new ServiceException("获取用户信息异常", HttpStatus.UNAUTHORIZED.value());
        }
    }
}
