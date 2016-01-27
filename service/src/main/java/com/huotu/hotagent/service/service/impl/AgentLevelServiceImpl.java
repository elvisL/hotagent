/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.service.impl;

import com.huotu.hotagent.service.entity.role.AgentLevel;
import com.huotu.hotagent.service.repository.role.AgentLevelRepository;
import com.huotu.hotagent.service.service.AgentLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by allan on 1/26/16.
 */
@Service
public class AgentLevelServiceImpl implements AgentLevelService {
    @Autowired
    private AgentLevelRepository agentLevelRepository;

    @Override
    public AgentLevel save(AgentLevel agentLevel) {
        return agentLevelRepository.save(agentLevel);
    }

    @Override
    public List<AgentLevel> agentLevelList() {
        return agentLevelRepository.findAll();
    }

    @Override
    public AgentLevel findByLevel(int level) {
        AgentLevel agentLevel = agentLevelRepository.findByLevel(level);
        return agentLevel;
    }

    @Override
    public boolean exist() {
        return agentLevelRepository.count() > 0;
    }
}
