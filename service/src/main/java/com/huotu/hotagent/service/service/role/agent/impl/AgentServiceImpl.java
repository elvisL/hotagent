/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.service.role.agent.impl;

import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.repository.role.agent.AgentRepository;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Override
    public long countByLevel(int level) {
        return agentRepository.countByLevel_level(level);
    }

    @Override
    public Page<Agent> findByLevel(int pageIndex, int pageSize, int level) {
        return agentRepository.findByLevel_level(level, new PageRequest(pageIndex - 1, pageSize, new Sort(Sort.Direction.DESC, "id")));
    }


    @Override
    public Page<Agent> findByLevelId(int levelId) {
        return null;
    }
}
