/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.model;

import lombok.Data;

/**
 * 代理商筛选条件
 * Created by allan on 2/3/16.
 */
@Data
public class AgentSearch {
    private String agentName;
    private String beginTime;
    private String endTime;
    /**
     * 代理商类型,-1表示全部代理商
     */
    private int agentType = -1;
    /**
     * 代理商等级,-1表示全部代理商
     */
    private int agentLevel = -1;
    /**
     * 上级代理id
     */
    private long parentAgent = 0;
}
