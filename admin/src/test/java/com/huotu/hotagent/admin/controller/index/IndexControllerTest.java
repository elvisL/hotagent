/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin.controller.index;

import com.huotu.hotagent.admin.WebTest;
import com.huotu.hotagent.admin.controller.index.pages.LoginPage;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by allan on 1/27/16.
 */
public class IndexControllerTest extends WebTest {

    @Test
    public void testLogin() throws Exception {
        String randomUsername = UUID.randomUUID().toString();
        String randomPassword = UUID.randomUUID().toString();
        String administrator = "administrator";
        String password = "hot!@#123";
        String loginError = null;
        webDriver.get("http://localhost:8080/login");
        LoginPage loginPage = initPage(LoginPage.class);
        //无效的用户名
        loginError = loginPage.submit(randomUsername, UUID.randomUUID().toString());
    }

    @Test
    public void testLoginError() throws Exception {

    }

    @Test
    public void testIndex() throws Exception {

    }
}