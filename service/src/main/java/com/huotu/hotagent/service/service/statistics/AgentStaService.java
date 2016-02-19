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
 * Created by chendeyu on 2/19/16.
 */
@Transactional(readOnly = true)
public interface AgentStaService {

    /**
     * 下级代理商总数
     *
     * @return
     */
    long agentNumWithLevel(Long id);

    /**
     * 总代理费用
     *
     * @return
     */
    double agentCosts(Long id);

    /**
     * 总返佣
     *
     * @return
     */
    double countCommission(Long id);

    /**
     * 账户余额
     *
     * @return
     */
    double balance(Long id);

    /**
     * 佣金余额
     *
     * @return
     */
    double commission(Long id);


}
