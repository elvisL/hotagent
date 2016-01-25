/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin.controller.agent;

import com.huotu.hotagent.admin.WebTest;
import com.huotu.hotagent.admin.controller.agent.pages.AgentEditPage;
import com.huotu.hotagent.common.ienum.EnumHelper;
import com.huotu.hotagent.service.common.AgentType;
import com.huotu.hotagent.service.entity.role.Agent;
import org.junit.Test;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by allan on 1/25/16.
 */
public class AgentControllerTest extends WebTest {

    @Test
    public void testAgentList() throws Exception {

    }

    @Test
    public void testAgentEdit() throws Exception {
        final Random random = new Random();
        Date now = new Date();
        webDriver.get("http://localhost:8080/agent/agentEdit");
        AgentEditPage agentEditPage = initPage(AgentEditPage.class);

        //开始构造一个虚拟的agent
        Agent randomAgent = new Agent();
        randomAgent.setUsername(UUID.randomUUID().toString());
        randomAgent.setPassword(UUID.randomUUID().toString());
        randomAgent.setCreateTime(now);
        randomAgent.setName(UUID.randomUUID().toString());
        randomAgent.setLevel(initLevel());
        int agentType = random.nextInt(2);
        AgentType randomType = EnumHelper.getEnumType(AgentType.class, agentType);
        randomAgent.setType(randomType);
        randomAgent.setBalance(random.nextInt(101));
        randomAgent.setProvince("浙江省");
        randomAgent.setCity("杭州市");
        randomAgent.setDistrict("滨江区");
    }
}