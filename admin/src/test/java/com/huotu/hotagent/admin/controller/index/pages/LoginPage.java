/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin.controller.index.pages;

import com.huotu.hotagent.admin.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

    public String submit(String username, String password) {
        //验证没有错误信息
        assertThat(loginError.getText()).isNullOrEmpty();

        this.username.clear();
        this.username.sendKeys(username);
        this.password.clear();
        this.password.sendKeys(password);

        loginBtn.click();

        PageFactory.initElements(webDriver, this);
        return loginError.getText();
    }
}
