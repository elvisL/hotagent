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
import com.huotu.hotagent.service.repository.product.ProductRepository;
import com.huotu.hotagent.service.repository.role.AgentRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * Created by allan on 1/25/16.
 */
public class AgentControllerTest extends WebTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AgentRepository agentRepository;

    @Test
    public void testAgentList() throws Exception {

    }

    @Test
    public void testAgentEdit() throws Exception {
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
        randomAgent.setContacts(UUID.randomUUID().toString());
        randomAgent.setPhoneNo(randomMobile());
        randomAgent.setAddress(UUID.randomUUID().toString());
        randomAgent.setMail(randomEmailAddress());
        randomAgent.setQq(randomMobile());
        randomAgent.setParent(null);
        randomAgent.setQualifyUri(UUID.randomUUID().toString());

        agentEditPage.submit(randomAgent);

        //// TODO: 1/25/16 断言,与数据库进行比较
    }
}