package com.zfy.mp.domain.dto.bwcj;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private Long column;
    // 配方名称
    private String pfmc;
    // 物料名称
    private String wlmc;
    // 出料开始时间
    private Date clkssj;

}
