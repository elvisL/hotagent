package com.huotu.hotagent.agent.model;

import lombok.Data;

/**
 * Created by chendeyu on 2016/2/19.
 * ������ͳ��
 */

@Data
public class AgentStatistics {


    /**
     * �¼�����������
     */
    private long agentNumWithLevel;

    /**
     * �ܴ������
     */
    private double agentCosts;

    /**
     * �ܷ�Ӷ
     */
    private double agentCommission;

    /**
     * �˻����
     */
    private double balance;

    /**
     * Ӷ�����
     */
    private double commission;


}
