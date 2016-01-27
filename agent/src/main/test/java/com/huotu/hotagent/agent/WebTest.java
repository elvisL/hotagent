/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.agent;

import com.huotu.hotagent.agent.config.MVCConfig;
import com.huotu.hotagent.agent.pages.AbstractPage;
import com.huotu.hotagent.service.common.ProductType;
import com.huotu.hotagent.service.config.ServiceConfig;
import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.entity.role.AgentLevel;
import com.huotu.hotagent.service.repository.product.ProductRepository;
import com.huotu.hotagent.service.repository.role.AgentLevelRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
    private AgentLevelRepository agentLevelRepository;
    @Autowired
    private ProductRepository productRepository;

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

    @Before
    public void initBaseData() throws Exception {
        initProduct();
    }

    /**
     * 初始化一个等级
     */
    public AgentLevel initLevel() {
        AgentLevel agentLevel1 = new AgentLevel();
        agentLevel1.setLevelName("一级代理");
        agentLevel1.setLevelDesc("一级代理");
        agentLevel1.setLevel(0);
        return agentLevelRepository.saveAndFlush(agentLevel1);
    }

    public void initProduct() {
        Product product1 = new Product();
        product1.setName("产品测试");
        product1.setProductType(ProductType.CESHI);
        product1.setProductDesc("产品测试");
        productRepository.saveAndFlush(product1);
    }
}
