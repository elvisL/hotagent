package com.huotu.hotagent.agent.model;

import lombok.Data;

/**
 * Created by chendeyu on 2016/2/19.
 * 代理商统计
 */

@Data
public class AgentStatistics {


    /**
     * 下级代理商总数
     */
    private long agentNumWithLevel;

    /**
     * 总代理费用
     */
    private double agentCosts;

    /**
     * 总返佣
     */
    private double agentCommission;

    /**
     * 账户余额
     */
    private double balance;

    /**
     * 佣金余额
     */
    private double commission;


}
