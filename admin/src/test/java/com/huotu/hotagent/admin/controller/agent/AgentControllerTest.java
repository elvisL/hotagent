/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin.controller.agent;

import com.huotu.hotagent.admin.common.AuthenticatedWebTest;
import com.huotu.hotagent.admin.common.LoginAs;
import com.huotu.hotagent.admin.controller.agent.pages.AgentEditPage;
import com.huotu.hotagent.admin.controller.agent.pages.AgentListPage;
import com.huotu.hotagent.common.ienum.EnumHelper;
import com.huotu.hotagent.service.common.AgentType;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.agent.AgentLevel;
import com.huotu.hotagent.service.model.AgentSearch;
import com.huotu.hotagent.service.repository.product.ProductRepository;
import com.huotu.hotagent.service.repository.role.agent.AgentLevelRepository;
import com.huotu.hotagent.service.repository.role.agent.AgentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by allan on 1/25/16.
 */
public class AgentControllerTest extends AuthenticatedWebTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private AgentLevelRepository levelRepository;
    private String mockAgentUsername;
    private String mockAgentPassword;
    private String mockAgentName;

    @Before
    public void initData() {
        mockAgentName = "代理商1";
        mockAgentUsername = UUID.randomUUID().toString();
        mockAgentPassword = UUID.randomUUID().toString();
    }

    @Test
    @LoginAs(isRoot = true)
    public void testAgentList() throws Exception {
        int agentCount = random.nextInt(50) + 1;
        //随机构造一些代理商
        for (int i = 0; i < agentCount; i++) {
            agentService.save(mockAgent());
        }
        webDriver.get("http://localhost/agents");
        AgentListPage agentListPage = initPage(AgentListPage.class);
        List<Agent> agents = agentRepository.findAll();
        agentListPage.setAgents(agents);
        List<AgentLevel> agentLevels = agentLevelService.agentLevelList();
        agentListPage.setAgentLevels(agentLevels);
        agentListPage.validate();

        //测试搜索
        AgentSearch randomSearch = new AgentSearch();
        randomSearch.setAgentLevel(randomAgentLevel().getLevel());
        randomSearch.setAgentType(randomAgentType().getValue());
        agentListPage.testSearch(randomSearch);
    }

    @Test
    @LoginAs(isRoot = true)
    public void testAgentEdit() throws Exception {
        Date now = new Date();
        webDriver.get("http://localhost:8080/agentEditForm");
        AgentEditPage agentEditPage = initPage(AgentEditPage.class);
        List<AgentLevel> agentLevels = levelRepository.findAll();
        //开始构造一个虚拟的agent
        Agent randomAgent = new Agent();
        randomAgent.setUsername(mockAgentUsername);
        randomAgent.setPassword(mockAgentPassword);
        randomAgent.setCreateTime(now);
        randomAgent.setName(mockAgentName);
        randomAgent.setLevel(agentLevels.get(0));
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
        randomAgent.setQualifyUri(UUID.randomUUID().toString());

        agentEditPage.submit(randomAgent);

        Agent agent = agentRepository.findByUsername(mockAgentUsername);
        Assert.assertEquals(mockAgentName, agent.getName());
        //// TODO: 1/25/16 断言,与数据库进行比较

    }
}