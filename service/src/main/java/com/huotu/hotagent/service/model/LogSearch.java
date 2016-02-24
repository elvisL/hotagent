package com.huotu.hotagent.service.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 余额日志查询条件
 * Created by admin on 2016/2/24.
 */
@Getter
@Setter
public class LogSearch {
    private String agentName;
//    private String supportName;
    private String customerName;
    private String beginTime;
    private String endTime;
    private long agent = 0;
}
