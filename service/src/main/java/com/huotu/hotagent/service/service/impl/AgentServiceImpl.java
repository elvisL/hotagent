package com.huotu.hotagent.service.service.impl;

import com.huotu.hotagent.service.entity.role.Agent;
import com.huotu.hotagent.service.repository.AgentRepository;
import com.huotu.hotagent.service.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chendeyu on 2016/1/25.
 */

@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    AgentRepository agentRepository;

    @Override
    public Agent findById(Long id) {
        Agent agent = agentRepository.findOne(id);
        return agent;
    }

    @Override
    public Agent save(Agent agent) {
        agentRepository.save(agent);
        return null;
    }
}
