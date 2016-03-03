package com.huotu.hotagent.service.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by chendeyu on 2016/3/1.
 */
@Getter
@Setter
public class WithdrawSearch {

    private int auditStatus=-1;
    private String beginTime;
    private String endTime;
    private long agentId;

}
