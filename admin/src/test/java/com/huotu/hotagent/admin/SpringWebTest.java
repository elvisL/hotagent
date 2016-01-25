/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin;

import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * Created by allan on 1/25/16.
 */
public class SpringWebTest {
    /**
     * 应用程序上下文
     */
    @Autowired(required = false)
    protected WebApplicationContext webApplicationContext;
    /**
     * servlet上下文
     */
    @Autowired(required = false)
    protected ServletContext servletContext;
    @Autowired(required = false)
    protected FilterChainProxy springSecurityFilter;
    @Autowired
    protected MockHttpServletRequest request;
    /**
     * 初始化
     */
    protected MockMvc mockMvc;
    protected WebClient webClient;
    protected WebDriver webDriver;
    /**
     * 允许在测试环境采用合适的webMvc的配置
     */
    @Autowired(required = false)
    private MockMvcConfigurer mockMvcConfigurer;

    /**
     * 初始化mockmvc
     */
    protected void createMockMvc() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(webApplicationContext);

        if (springSecurityFilter != null) {
            builder = builder.addFilters(springSecurityFilter);
        }

        if (mockMvcConfigurer != null) {
            builder = builder.apply(mockMvcConfigurer);
        }
        this.mockMvc = builder.build();
    }

    @Before
    public void setUp() throws Exception {
        //初始化mockMvc
        this.createMockMvc();
        //初始化webClient和driver
        this.webClient = MockMvcWebClientBuilder.mockMvcSetup(this.mockMvc).build();
        this.webDriver = MockMvcHtmlUnitDriverBuilder.mockMvcSetup(this.mockMvc).build();
    }

    @After
    public void afterTest() {
        if (webDriver != null) {
            webDriver.close();
        }
    }
}
