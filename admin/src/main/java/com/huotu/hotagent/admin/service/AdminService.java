/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin.service;

import com.huotu.hotagent.admin.model.StatisticsModel;
import com.huotu.hotagent.service.service.statistics.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by allan on 1/30/16.
 */
@Service
public class AdminService {
    @Autowired
    private StatisticsService statisticsService;


    public StatisticsModel statisticsModel() {
        StatisticsModel statisticsModel = new StatisticsModel();
        statisticsModel.setNewAgentDay(statisticsService.newAgentDay());
        statisticsModel.setNewAgentMonth(statisticsService.newAgentMonth());
        statisticsModel.setAgentNumWithLevel1(statisticsService.agentNumWithLevel1());
        statisticsModel.setAgentNumWithUnder(statisticsService.agentNumWithUnder());
        statisticsModel.setNormalAgentNum(statisticsService.normalAgentNum());
        statisticsModel.setSoleAgentNum(statisticsService.soleAgentNum());
        statisticsModel.setUnHandleWithdraw(statisticsService.unHandleWithdraw());
        
        return statisticsModel;
    }
}
