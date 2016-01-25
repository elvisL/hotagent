package com.huotu.hotagent.service.service;

import com.huotu.hotagent.service.entity.role.Agent;

/**
 * 代理商
 * Created by chendeyu on 2016/1/25.
 */
public interface AgentService {

    Agent findById(Long id);

    Agent save(Agent agent);
}
