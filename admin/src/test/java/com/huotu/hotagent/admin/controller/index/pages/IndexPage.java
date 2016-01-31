/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin.controller.index.pages;

import com.huotu.hotagent.admin.model.StatisticsModel;
import com.huotu.hotagent.admin.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by allan on 1/29/16.
 */
public class IndexPage extends AbstractPage {

    @FindBy(className = "left-new-day")
    private WebElement leftNewDay;
    @FindBy(className = "left-new-month")
    private WebElement leftNewMonth;
    @FindBy(className = "left-agentNum-level1")
    private WebElement leftAgentNumWithLevel1;
    @FindBy(className = "left-agentNum-under")
    private WebElement leftAgentNumWithUnder;
    @FindBy(className = "left-agentNum-normal")
    private WebElement leftAgentNumNormal;
    @FindBy(className = "left-agentNum-sole")
    private WebElement leftAgentNumSole;

    @FindBy(className = "index-agentNum-level1")
    private WebElement indexAgentNumWithLevel1;
    @FindBy(className = "index-agentNum-under")
    private WebElement indexAgentNumWithUnder;
    @FindBy(className = "index-agentNum-normal")
    private WebElement indexAgentNumNormal;
    @FindBy(className = "index-agentNum-sole")
    private WebElement indexAgentNumSole;
    @FindBy(className = "index-new-day")
    private WebElement indexNewDay;
    @FindBy(className = "index-new-month")
    private WebElement indexNewMonth;
    @FindBy(className = "no-handler-withdraw")
    private WebElement unHandlerWithdraw;


    public IndexPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void validate() {

    }

    public void validateStatistics(StatisticsModel statisticsModel) {
        assertThat(leftNewDay.getText())
                .isEqualTo(statisticsModel.getNewAgentDay());
        assertThat(leftNewMonth.getText())
                .isEqualTo(statisticsModel.getNewAgentMonth());
        assertThat(leftAgentNumWithLevel1.getText())
                .isEqualTo(statisticsModel.getAgentNumWithLevel1());
        assertThat(leftAgentNumWithUnder.getText())
                .isEqualTo(statisticsModel.getAgentNumWithUnder());
        assertThat(leftAgentNumNormal.getText())
                .isEqualTo(statisticsModel.getNormalAgentNum());
        assertThat(leftAgentNumSole.getText())
                .isEqualTo(statisticsModel.getSoleAgentNum());
        assertThat(indexNewDay.getText())
                .isEqualTo(statisticsModel.getNewAgentDay());
        assertThat(indexNewMonth.getText())
                .isEqualTo(statisticsModel.getNewAgentMonth());
        assertThat(indexAgentNumWithLevel1.getText())
                .isEqualTo(statisticsModel.getAgentNumWithLevel1());
        assertThat(indexAgentNumWithUnder.getText())
                .isEqualTo(statisticsModel.getAgentNumWithUnder());
        assertThat(indexAgentNumNormal.getText())
                .isEqualTo(statisticsModel.getNormalAgentNum());
        assertThat(indexAgentNumSole.getText())
                .isEqualTo(statisticsModel.getSoleAgentNum());
        assertThat(unHandlerWithdraw.getText())
                .isEqualTo(statisticsModel.getUnHandleWithdraw());
    }
}
