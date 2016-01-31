/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin.common;

import com.huotu.hotagent.admin.controller.index.pages.LoginPage;
import com.huotu.hotagent.common.constant.SysConstant;
import com.huotu.hotagent.service.common.Authority;
import com.huotu.hotagent.service.entity.role.manager.Manager;
import com.huotu.hotagent.service.service.role.manager.ManagerService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 已登录的web测试基类,需要使用{@link LoginAs}在方法上表明当前登录用户的权限
 * Created by allan on 1/29/16.
 */
@RunWith(AuthenticatedWebTest.AuthenticatedRunner.class)
public class AuthenticatedWebTest extends WebTest {
    protected Manager currentManager;
    private ManagerService managerService;
    private LoginAs loginAs;

    @Before
    public void AuthInit() throws Exception {
        String username = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();

        if (loginAs == null) {
            throw new IllegalStateException("未标注LoginAs,无法识别当前登录角色的权限");
        }

        if (loginAs.isRoot()) {
            username = SysConstant.ROOT_USER;
            password = SysConstant.ROOT_PASS;
            currentManager = managerService.findByUsername(SysConstant.ROOT_USER);
        } else {
            Set<Authority> authorities = new HashSet<>();
            for (Authority authority : loginAs.value()) {
                authorities.add(authority);
            }
            currentManager = mockManager(username, password, authorities);
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
