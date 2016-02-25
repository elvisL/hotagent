package com.huotu.hotagent.service.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by chendeyu on 2016/2/2.
 */
@Getter
@Setter
public class AgentProduct {
    private long productId;
    private String productName;
    //产品单价
    private double productPrice;
    //销售总额
    private double account;
}
