/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.service.role.agent.impl;

import com.huotu.hotagent.common.constant.ApiResult;
import com.huotu.hotagent.common.constant.ResultCodeEnum;
import com.huotu.hotagent.common.constant.StringConstant;
import com.huotu.hotagent.common.ienum.EnumHelper;
import com.huotu.hotagent.common.utils.StringUtil;
import com.huotu.hotagent.service.common.AgentType;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.model.AgentSearch;
import com.huotu.hotagent.service.repository.role.agent.AgentRepository;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chendeyu on 2016/1/25.
 */

@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentRepository agentRepository;

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

    @Override
    public Page<Agent> findAll(int pageIndex, int pageSize, AgentSearch agentSearch) {
        Specification<Agent> specification = ((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isEmpty(agentSearch.getAgentName())) {
                predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + agentSearch.getAgentName() + "%"));
            }
            if (!StringUtils.isEmpty(agentSearch.getBeginTime())) {
                Date beginDate = StringUtil.DateFormat(agentSearch.getBeginTime(), StringConstant.DATE_PATTERN);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Date.class), beginDate));
            }
            if (!StringUtils.isEmpty(agentSearch.getEndTime())) {
                LocalDate endDate = LocalDate.parse(agentSearch.getEndTime(), DateTimeFormatter.ofPattern(StringConstant.DATE_PATTERN));
                endDate = endDate.plusDays(1);
                Date endTime = Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(endDate);
                predicates.add(criteriaBuilder.lessThan(root.get("createTime").as(Date.class), endTime));
            }
            if (agentSearch.getAgentType() > -1) {
                AgentType agentType = EnumHelper.getEnumType(AgentType.class, agentSearch.getAgentType());
                predicates.add(criteriaBuilder.equal(root.get("type"), agentType));
            }
            if (agentSearch.getAgentLevel() > -1) {
                predicates.add(criteriaBuilder.equal(root.get("level").get("levelId").as(Integer.class), agentSearch.getAgentLevel()));
            }
            if (agentSearch.getParentAgent() > -1) {
                if (agentSearch.getParentAgent() == 0) {
                    predicates.add(criteriaBuilder.isNull(root.get("parent").as(Agent.class)));
                } else {
                    predicates.add(criteriaBuilder.equal(root.get("parent").get("id").as(Integer.class), agentSearch.getParentAgent()));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
        return agentRepository.findAll(specification, new PageRequest(pageIndex - 1, pageSize, new Sort(Sort.Direction.DESC, "id")));
    }

    @Override
    public ApiResult delAgent(Long id) {
        ApiResult apiResult = null;
        agentRepository.delete(id);
        apiResult = ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        return apiResult;
    }

    @Override
    public ApiResult lockAgent(Long id) {

        ApiResult apiResult = null;
        Agent agent = agentRepository.findOne(id);
        agent.setAccountNonLocked(true);
        agentRepository.save(agent);
        apiResult = ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        return apiResult;
    }

    @Override
    public Agent findByUsername(String userName) {
        Agent agent = agentRepository.findByUsername(userName);
        return agent;
    }
}
