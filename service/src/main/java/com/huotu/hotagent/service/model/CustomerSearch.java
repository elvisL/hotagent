package com.huotu.hotagent.service.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by chendeyu on 2016/2/26.
 * 产品客户详情
 */

@Getter
@Setter
public class CustomerSearch {


    private String customerName;
    private Long productId;
    private String beginTime;
    private String endTime;
    private Long agentId;
    private boolean aBoolean;//判断是否为伙伴商场,true为是伙伴商场

}
