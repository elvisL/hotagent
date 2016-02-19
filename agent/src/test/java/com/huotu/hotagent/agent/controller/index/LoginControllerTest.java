/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.agent.controller.index;

import com.huotu.hotagent.agent.common.WebTest;
import com.huotu.hotagent.agent.controller.index.pages.LoginPage;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;


/**
 * Created by allan on 1/27/16.
 */
public class LoginControllerTest extends WebTest {
    @Test
    @Rollback
    public void testLogin() throws Exception {
        Agent root = agentService.findByUsername("admin");

        webDriver.get("http://localhost");
        LoginPage loginPage = initPage(LoginPage.class);
        loginPage.validate();

        //错误的用户名密码
        loginPage.wrongUsernameAndPass();
        //默认供应商登录验证
        loginPage.administratorLogin(root);

        //新建一个代理商登录

        Agent mockAgent = mockAgent();
        loginService.newLogin(mockAgent,mockAgent.getPassword());
        loginPage.login(mockAgent.getUsername(), mockAgent.getPassword());

        //锁定的操作员
//        mockManager.setAccountNonLocked(false);
//        managerService.save(mockManager);
//        loginPage.lockedOperatorLogin(mockManager, randomPassword);
    }
}