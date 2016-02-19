/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.agent.common;/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */


import com.huotu.hotagent.agent.config.MVCConfig;
import com.huotu.hotagent.agent.pages.AbstractPage;
import com.huotu.hotagent.common.constant.SysConstant;
import com.huotu.hotagent.service.common.AgentType;
import com.huotu.hotagent.service.common.Authority;
import com.huotu.hotagent.service.config.ServiceConfig;
import com.huotu.hotagent.service.entity.product.Price;
import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.agent.AgentLevel;
import com.huotu.hotagent.service.service.product.ProPriceService;
import com.huotu.hotagent.service.service.product.ProductService;
import com.huotu.hotagent.service.service.role.agent.AgentLevelService;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import com.huotu.hotagent.service.service.role.agent.LoginService;
import org.junit.runner.RunWith;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by allan on 1/25/16.
 */
@ActiveProfiles("test")
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MVCConfig.class, ServiceConfig.class})
@Transactional
public abstract class WebTest extends SpringWebTest {

    @Autowired
    protected AgentLevelService agentLevelService;
    @Autowired
    protected AgentService agentService;
    @Autowired
    protected ProductService productService;
    @Autowired
    protected ProPriceService proPriceService;
    @Autowired
    protected LoginService loginService;

    /**
     * 初始化逻辑页面
     *
     * @param clazz 该页面相对应的逻辑页面的类
     * @param <T>   该页面相对应的逻辑页面
     * @return 页面实例
     */
    public <T extends AbstractPage> T initPage(Class<T> clazz) {
        T page = PageFactory.initElements(webDriver, clazz);
        page.setTestInstance(this);
        return page;
    }

    protected Set<Authority> randomAuthority() {
        Set<Authority> authoritySet = new HashSet<>();
        Authority[] authorities = Authority.values();
        int totalSize = authorities.length - 2;
        int randomSize = random.nextInt(totalSize) + 1;
        for (int i = 0; i < randomSize; i++) {
            int randomAuthIndex = random.nextInt(totalSize);
            while (authorities[randomAuthIndex] == Authority.AGENT_ROOT || authorities[randomAuthIndex] == Authority.MANAGER_ROOT) {
                randomAuthIndex = random.nextInt(totalSize);
            }
            authoritySet.add(authorities[randomAuthIndex]);
        }
        return authoritySet;
    }

    /**
     * 随机一个代理商类型
     *
     * @return
     */
    protected AgentType randomAgentType() {
        AgentType[] agentTypes = AgentType.values();
        int randomIndex = random.nextInt(agentTypes.length);
        return agentTypes[randomIndex];
    }

    /**
     * 随机一个代理商等级
     *
     * @return
     */
    protected AgentLevel randomAgentLevel() {
        List<AgentLevel> agentLevels = agentLevelService.agentLevelList();
        int randomIndex = random.nextInt(agentLevels.size());
        return agentLevels.get(randomIndex);
    }

    /**
     * 创建一个没有权限的管理员
     *
     * @return
     */
//    protected Manager mockManager(String username, String password, Set<Authority> authorities) {
//        Manager mockManager = new Manager();
//        mockManager.setUsername(username);
//        mockManager.setPassword(password);
//        mockManager.setRoleName(UUID.randomUUID().toString());
//        mockManager.setName(UUID.randomUUID().toString());
//        mockManager.setCreateTime(new Date());
//        mockManager.setAuthorities(authorities);
//        return managerService.save(mockManager);
//    }

    /**
     * 建立一个代理商
     * 代理商等级,类型以及上线均为随机
     * <p>
     * 不会返回null
     *
     * @return
     */
    protected Agent mockAgent() {
        //先随机一个等级
        AgentLevel randomLevel = randomAgentLevel();
        int parentLevel = randomLevel.getLevel() - 1;
        Page<Agent> parentAgents = agentService.findByLevel(1, SysConstant.DEFAULT_PAGE_SIZE, parentLevel);
        while (parentAgents.getContent().size() == 0 && randomLevel.getLevel() > 0) {
            randomLevel = randomAgentLevel();
            parentAgents = agentService.findByLevel(1, SysConstant.DEFAULT_PAGE_SIZE, parentLevel);
        }
        Agent randomParent = randomLevel.getLevel() > 0 ? parentAgents.getContent().get(random.nextInt(parentAgents.getContent().size())) : null;

        Agent mockAgent = new Agent();
        mockAgent.setUsername(UUID.randomUUID().toString());
        mockAgent.setPassword(UUID.randomUUID().toString());
        mockAgent.setName(UUID.randomUUID().toString());
        mockAgent.setLevel(randomLevel);
        mockAgent.setType(randomAgentType());
        mockAgent.setBalance(random.nextDouble());
        mockAgent.setCommission(random.nextDouble());
        mockAgent.setProvince(UUID.randomUUID().toString());
        mockAgent.setCity(UUID.randomUUID().toString());
        mockAgent.setDistrict(UUID.randomUUID().toString());
        mockAgent.setContacts(UUID.randomUUID().toString());
        mockAgent.setPhoneNo(randomMobile());
        mockAgent.setAddress(UUID.randomUUID().toString());
        mockAgent.setMail(randomEmailAddress());
        mockAgent.setQq(UUID.randomUUID().toString());
        mockAgent.setParent(randomParent);
        mockAgent.setQualifyUri(UUID.randomUUID().toString());

        //插入产品价格关联表
        List<Product> products = productService.findAll();
        Set<Price> prices = new HashSet<>();
        if (randomLevel.getLevel() == 0) {
            //使用基础价格
            for (Product product : products) {
                Price price = new Price();
                price.setAgent(mockAgent);
                price.setPrice(product.getBasePrice());
                price.setProduct(product);

                prices.add(price);
            }
        } else {
            //比上级代理的价格随机高出一点即是符合期望
            Set<Price> parentPrices = randomParent.getPrices();
            for (Price parentPrice : parentPrices) {
                Price price = new Price();
                price.setAgent(mockAgent);
                price.setPrice(parentPrice.getPrice() + random.nextDouble() + 1);
                price.setProduct(parentPrice.getProduct());

                prices.add(price);
            }
        }
        mockAgent.setPrices(prices);

        return mockAgent;
    }
}
