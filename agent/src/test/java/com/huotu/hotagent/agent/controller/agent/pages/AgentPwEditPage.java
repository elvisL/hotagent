package com.huotu.hotagent.agent.controller.agent.pages;

import com.huotu.hotagent.agent.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.UUID;

/**
 * Created by chendeyu on 2016/2/22.
 */
public class AgentPwEditPage extends AbstractPage {

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
    @FindBy(css = "input[name=newPassword]")
    private WebElement newPassword;
    @FindBy(css = "input[name=CnewPassword]")
    private WebElement CnewPassword;




    public AgentPwEditPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void validate() {

    }

    /**
     * ´íÎóµÄÃÜÂë
     */
    public void wrongPassword() {
        String randomPassword = UUID.randomUUID().toString();


        password.clear();
        password.sendKeys(randomPassword);
        newPassword.clear();
        newPassword.sendKeys(randomPassword);
        CnewPassword.clear();
        CnewPassword.sendKeys(randomPassword);

        submitButton.click();




    }



}
