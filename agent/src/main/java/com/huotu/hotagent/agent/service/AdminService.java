/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.agent.service;

import com.huotu.hotagent.agent.model.AgentStatistics;
import com.huotu.hotagent.service.service.statistics.AgentStaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by allan on 1/30/16.
 */
@Service
public class AdminService {
    @Autowired
    private AgentStaService agentStaService;

    public AgentStatistics agentStatistics(Long id) {
        AgentStatistics agentStatistics = new AgentStatistics();
        agentStatistics.setAgentCommission(agentStaService.countCommission(id));
        agentStatistics.setAgentCosts(agentStaService.agentCosts(id));
        agentStatistics.setBalance(agentStaService.balance(id));
        agentStatistics.setCommission(agentStaService.commission(id));
        agentStatistics.setAgentNumWithLevel(agentStaService.agentNumWithLevel(id));
        agentStatistics.setUnPassWithdraw(agentStaService.unPassWithdraw(id));
        return agentStatistics;
    }
}
