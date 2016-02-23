/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.agent.controller.index.pages;

import com.huotu.hotagent.agent.common.ExceptionHandler;
import com.huotu.hotagent.agent.pages.AbstractPage;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.manager.Manager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by allan on 1/27/16.
 */
public class LoginPage extends AbstractPage {
    @FindBy(css = "form")
    private WebElement form;
    @FindBy(css = "input[name=username]")
    private WebElement username;
    @FindBy(css = "input[name=password]")
    private WebElement password;
    @FindBy(css = ".btn-block")
    private WebElement loginBtn;
    @FindBy(css = "#loginError")
    private WebElement loginError;

    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void validate() {
        //验证没有错误信息
        assertThat(loginError.getText()).isNullOrEmpty();
    }


    public String submit(String username, String password) {
        this.username.clear();
        this.username.sendKeys(username);
        this.password.clear();
        this.password.sendKeys(password);

        loginBtn.click();
        String pre = webDriver.findElement(By.id("loginError")).getText();
        PageFactory.initElements(webDriver, this);
        String after = webDriver.findElement(By.id("loginError")).getText();
        return loginError.getText();
    }

    /**
     * 错误的帐号密码
     */
    public void wrongUsernameAndPass() {
        String randomUsername = UUID.randomUUID().toString();
        String randomPassword = UUID.randomUUID().toString();

        username.clear();
        username.sendKeys(randomUsername);
        password.clear();
        password.sendKeys(randomPassword);

        loginBtn.click();
        String error = webDriver.findElement(By.id("loginError")).getText();
        assertThat(error).isNotEmpty();
        assertThat(error).isNotNull();
        assertThat(error).isEqualTo(ExceptionHandler.BAD_CREDENTIALS_MSG);
    }

    /**
     * 默认供应商登录
     *
     * @param agent
     */
    public void administratorLogin(Agent agent) {
        this.username.clear();
        this.username.sendKeys(agent.getUsername());
        this.password.clear();
        this.password.sendKeys("admin");

        loginBtn.click();

        assertThat(webDriver.getCurrentUrl()).contains("index");
        //可以看到所有菜单


        logout();
    }

    /**
     * 供应商登录
     * 前提:未锁定
     *
     * @param agent
     * @param password
     * @return
     */
    public void operatorLogin(Agent agent, String password) {
        this.username.clear();
        this.username.sendKeys(agent.getUsername());
        this.password.clear();
        this.password.sendKeys(password);

        loginBtn.click();

        assertThat(webDriver.getCurrentUrl().contains("index"));
        logout();
    }

    public void lockedOperatorLogin(Manager manager, String password) {
        this.username.clear();
        this.username.sendKeys(manager.getUsername());
        this.password.clear();
        this.password.sendKeys(password);

        loginBtn.click();

        String error = webDriver.findElement(By.id("loginError")).getText();
        assertThat(error).isNotEmpty();
        assertThat(error).isNotNull();
        assertThat(error).isEqualTo(ExceptionHandler.LOCKED_MSG);
    }

    private void logout() {
        //退出登录
        webDriver.findElement(By.cssSelector(".headermenu button[class~=dropdown-toggle]")).click();
        webDriver.findElement(By.id("aLogout")).click();
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     */
    public void login(String username, String password) {
        this.username.clear();
        this.username.sendKeys(username);
        this.password.clear();
        this.password.sendKeys(password);

        loginBtn.click();
    }
}
