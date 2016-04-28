/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.service.statistics;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by allan on 1/30/16.
 */
@Transactional(readOnly = true)
public interface StatisticsService {
    /**
     * 今日新增代理商总数
     *
     * @return
     */
    long newAgentDay();

    /**
     * 本月新增代理商总数
     *
     * @return
     */
    long newAgentMonth();

    /**
     * 一级代理商总数
     *
     * @return
     */
    long agentNumWithLevel1();

    /**
     * 下级代理商总数
     *
     * @return
     */
    long agentNumWithUnder();

    /**
     * 普通代理总数
     *
     * @return
     */
    long normalAgentNum();

    /**
     * 独家代理总数
     *
     * @return
     */
    long soleAgentNum();

    /**
     * 省级代理总数
     *
     * @return
     */
    long proAgentNum();


    /**
     * 未处理提现申请数量
     *
     * @return
     */
    long unHandleWithdraw();

    /**
     * 某个代理商下的所有代理商数量
     *
     * @return
     */
    long numBelongAgent(int agentId);
}
