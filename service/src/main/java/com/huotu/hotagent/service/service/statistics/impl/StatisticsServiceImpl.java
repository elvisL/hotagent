/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.service.statistics.impl;

import com.huotu.hotagent.service.common.AgentType;
import com.huotu.hotagent.service.common.AuditStatus;
import com.huotu.hotagent.service.repository.record.WithdrawRepository;
import com.huotu.hotagent.service.repository.role.agent.AgentRepository;
import com.huotu.hotagent.service.service.statistics.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by allan on 1/30/16.
 */
@Service
@Transactional(readOnly = true)
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private WithdrawRepository withdrawRepository;

    @Override
    public long newAgentDay() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        Date date = Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(startOfDay);
        return agentRepository.countByCreateTimeAfter(date);
    }

    @Override
    public long newAgentMonth() {
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        Date date = Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(startOfMonth);
        return agentRepository.countByCreateTimeAfter(date);
    }

    @Override
    public long agentNumWithLevel1() {
        return agentRepository.countByLevel_level(0);
    }

    @Override
    public long agentNumWithUnder() {
        return agentRepository.countByLevel_levelGreaterThan(0);
    }

    @Override
    public long normalAgentNum() {
        return agentRepository.countByType(AgentType.NORMAL);
    }

    @Override
    public long soleAgentNum() {
        return agentRepository.countByType(AgentType.SOLE);
    }

    @Override
    public long proAgentNum() {
        return agentRepository.countByType(AgentType.PRO);
    }

    @Override
    public long unHandleWithdraw() {
        return withdrawRepository.countByAuditStatus(AuditStatus.APPLYING);
    }

    @Override
    public long numBelongAgent(int agentId) {
        return agentRepository.countByParent_id(agentId);
    }
}
