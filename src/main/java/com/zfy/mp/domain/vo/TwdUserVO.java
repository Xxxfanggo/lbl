package com.zfy.mp.domain.vo;

import lombok.Data;

/**
 *
 * @文件名: TwdUserVO.java
 * @工程名: mp
 * @包名: com.zfy.mp.vo.user
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2025-12-23 10:08
 * @版本号: V2.4.0
 */
@Data
public class TwdUserVO {
    private String sub;
    private String name;
    private String phone;
    private String jwtToken;
}
