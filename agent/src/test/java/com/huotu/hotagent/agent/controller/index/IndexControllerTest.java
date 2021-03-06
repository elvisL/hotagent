/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.agent.controller.index;

import com.huotu.hotagent.agent.common.AuthenticatedWebTest;
import com.huotu.hotagent.agent.common.LoginAs;
import com.huotu.hotagent.agent.controller.index.pages.IndexPage;
import com.huotu.hotagent.agent.model.AgentStatistics;
import com.huotu.hotagent.agent.service.AdminService;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by allan on 1/29/16.
 */
public class IndexControllerTest extends AuthenticatedWebTest {

    @Autowired
    private AdminService adminService;

    @Test
    @LoginAs
    public void testIndex() throws Exception {

        Agent agent = agentService.findByUsername("admin");
        AgentStatistics agentStatistics = adminService.agentStatistics(agent.getId());

        webDriver.get("http://localhost");
        IndexPage indexPage = initPage(IndexPage.class);


        indexPage.validateStatistics(agentStatistics);
    }
}
