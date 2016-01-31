/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin.model;

import lombok.Data;

/**
 * Created by allan on 1/30/16.
 */
@Data
public class StatisticsModel {
    /**
     * 今日新增
     */
    private long newAgentDay;
    /**
     * 本月新增
     */
    private long newAgentMonth;
    /**
     * 一级代理商总数
     */
    private long agentNumWithLevel1;
    /**
     * 下级代理总数
     */
    private long agentNumWithUnder;
    /**
     * 普通代理总数
     */
    private long normalAgentNum;
    /**
     * 独家代理总数
     */
    private long soleAgentNum;
    /**
     * 为处理体现申请
     */
    private long unHandleWithdraw;
}
