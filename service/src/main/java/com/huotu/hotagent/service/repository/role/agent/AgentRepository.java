/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.repository.role.agent;

import com.huotu.hotagent.service.common.AgentType;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by allan on 1/25/16.
 */
@Repository
public interface AgentRepository extends JpaRepository<Agent, Long>, JpaSpecificationExecutor {
    Agent findByUsername(String mockAgentUsername);

    Page<Agent> findByLevel_level(int level, Pageable pageable);

    long countByLevel_level(int level);

    long countByLevel_levelGreaterThan(int level);

    long countByParent_id(int agentId);

    long countByParent_id(Long id);

    long countByCreateTimeAfter(Date date);

    long countByType(AgentType type);
}
