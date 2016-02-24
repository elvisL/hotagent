package com.huotu.hotagent.service.service.statistics.impl;

import com.huotu.hotagent.service.entity.log.CommissionLog;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.repository.log.CommissionLogRepository;
import com.huotu.hotagent.service.repository.role.agent.AgentRepository;
import com.huotu.hotagent.service.service.statistics.AgentStaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chendeyu on 2016/2/19.
 */
@Service
public class AgentStaServiceImpl implements AgentStaService {

    @Autowired
    AgentRepository agentRepository;

    @Autowired
    CommissionLogRepository commissionLogRepository;



    @Override
    public long agentNumWithLevel(Long id) {
        return agentRepository.countByParent_id(id);
    }

    @Override
    public double agentCosts(Long id) {
        return 10000;
    }

    @Override
    public double countCommission(Long id) {
        List<CommissionLog> commissionLogList = commissionLogRepository.findByAgent_id(Long.valueOf(id));
        double count=0;
        if (commissionLogList.size()!=0) {
            for (CommissionLog commissionLog : commissionLogList) {
                count = count + commissionLog.getImportMoney();
            }
        }
        return count;
    }

    @Override
    public double balance(Long id) {
        Agent agent = agentRepository.findOne(id);
        return agent.getBalance();
    }

    @Override
    public double commission(Long id) {
        Agent agent = agentRepository.findOne(id);
        return agent.getCommission();
    }
}
