/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.agent.common;

import com.gargoylesoftware.htmlunit.WebClient;
import org.apache.commons.lang3.RandomStringUtils;
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
import java.util.Random;

/**
 * Created by allan on 1/25/16.
 */
public class SpringWebTest {
    protected final Random random = new Random();
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
    public void initTest() throws Exception {
        //初始化mockMvc
        this.createMockMvc();
        //初始化webClient和driver
        this.webClient = MockMvcWebClientBuilder
                .mockMvcSetup(this.mockMvc)
                .build();
        this.webDriver = MockMvcHtmlUnitDriverBuilder
                .mockMvcSetup(this.mockMvc)
                .build();
    }

    @After
    public void afterTest() {
        if (webDriver != null) {
            webDriver.close();
        }
    }

    /**
     * <p>位数不足无法保证其唯一性,需要客户端代码自行校验唯一性.</p>
     * <p>具体的区间是10000000000-19999999999</p>
     *
     * @return 获取一个随机的手机号码
     */
    protected String randomMobile() {
        String p1 = String.valueOf(100000 + random.nextInt(100000));
        //还有5位 而且必须保证5位
        String p2 = String.format("%05d", random.nextInt(100000));
        return p1 + p2;
    }

    /**
     * @return 获取随机email地址
     */
    protected String randomEmailAddress() {
        return RandomStringUtils.randomAlphabetic(random.nextInt(5) + 3)
                + "@"
                + RandomStringUtils.randomAlphabetic(random.nextInt(5) + 3)
                + "."
                + RandomStringUtils.randomAlphabetic(random.nextInt(2) + 2);
    }
}
