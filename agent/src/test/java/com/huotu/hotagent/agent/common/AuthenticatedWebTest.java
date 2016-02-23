/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.agent.common;

import com.huotu.hotagent.agent.controller.index.pages.LoginPage;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 已登录的web测试基类,需要使用{@link LoginAs}在方法上表明当前登录用户的权限
 * Created by allan on 1/29/16.
 */
@RunWith(AuthenticatedWebTest.AuthenticatedRunner.class)
public class AuthenticatedWebTest extends WebTest {
    private LoginAs loginAs;

    @Before
    public void AuthInit() throws Exception {
        String username = "testAgent";
        String password = "testAgent";
        Agent testAgent = agentService.findByUsername(username);
        if (testAgent == null){
            loginService.newLogin(mockAgent(username,password),mockAgent(username,password).getPassword());//保存mockAgent
        }

        webDriver.get("http://localhost");
        LoginPage loginPage = initPage(LoginPage.class);
        loginPage.login(username, password);

    }

    public static class AuthenticatedRunner extends SpringJUnit4ClassRunner {

        public AuthenticatedRunner(Class<?> clazz) throws InitializationError {
            super(clazz);
        }

        @Override
        protected Statement withBefores(FrameworkMethod frameworkMethod, Object testInstance, Statement statement) {
            ((AuthenticatedWebTest) testInstance).loginAs = frameworkMethod.getAnnotation(LoginAs.class);
            return super.withBefores(frameworkMethod, testInstance, statement);
        }
    }
}
