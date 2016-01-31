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
import com.huotu.hotagent.service.entity.role.agent.Agent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by allan on 1/25/16.
 */
public class AgentEditPage extends AbstractPage {

    @FindBy(css = "button[class~=btn-primary]")
    private WebElement submitButton;
    @FindBy(css = "#editForm")
    private WebElement form;
    @FindBy(css = "input[name='name']")
    private WebElement agentName;
    @FindBy(css = "input[name='username']")
    private WebElement username;
    @FindBy(css = "input[name='password']")
    private WebElement password;

    public AgentEditPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void validate() {

    }

    /**
     * 提交表单
     *
     * @param randomAgent
     */
    public void submit(Agent randomAgent) {
        agentName.clear();
        agentName.sendKeys(randomAgent.getName());
        username.clear();
        username.sendKeys(randomAgent.getUsername());
        password.clear();
        password.sendKeys(randomAgent.getPassword());
        submitButton.click();
    }
}
