/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin.controller.agent.pages;

import com.huotu.hotagent.admin.pages.AbstractPage;
import com.huotu.hotagent.service.entity.role.Agent;
import org.openqa.selenium.WebDriver;

/**
 * Created by allan on 1/25/16.
 */
public class AgentEditPage extends AbstractPage {
    public AgentEditPage(WebDriver webDriver) {
        super(webDriver);
    }

    /**
     * 提交表单
     *
     * @param randomAgent
     */
    public void submit(Agent randomAgent) {

    }
}
