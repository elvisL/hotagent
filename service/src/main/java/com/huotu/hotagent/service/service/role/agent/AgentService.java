/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.service.role.agent;

import com.huotu.hotagent.common.constant.ApiResult;
import com.huotu.hotagent.service.common.AgentType;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.model.AgentSearch;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 代理商
 * Created by chendeyu on 2016/1/25.
 */
public interface AgentService {

    Agent findById(Long id);

    Agent save(Agent agent);

    ApiResult delAgent(Long id);

    ApiResult lockAgent(Long id,int bl);

    Agent findByUsername(String userName);

    /**
     * 得到当前等级下的代理商数量
     *
     * @param level
     * @return
     */
    long countByLevel(int level);

    Page<Agent> findByLevel(int pageIndex, int pageSize, int level);

    Page<Agent> findByLevelId(int levelId);

    Page<Agent> findAll(int pageIndex, int pageSize, AgentSearch agentSearch);

    List<Agent> findByCityAndType(String city,AgentType type,Agent agent);

    List<Agent> findByProvinceAndType(String province,AgentType type,Agent agent);
}
