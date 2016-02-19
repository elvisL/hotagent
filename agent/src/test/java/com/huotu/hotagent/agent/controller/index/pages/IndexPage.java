/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.agent.controller.index.pages;

import com.huotu.hotagent.agent.model.AgentStatistics;
import com.huotu.hotagent.agent.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by chendeyu on 2/19/16.
 */
public class IndexPage extends AbstractPage {

    @FindBy(className = "index-agentNum-level1")
    private WebElement indexAgentNumWithLevel1;
    @FindBy(className = "index-agent-costs")
    private WebElement indexAgentCosts;
    @FindBy(className = "index-agent-commission")
    private WebElement indexAgentCommission;
    @FindBy(className = "index-balance")
    private WebElement indexBalance;
    @FindBy(className = "index-commission")
    private WebElement indexCommission;


    public IndexPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void validate() {

    }

    public void validateStatistics(AgentStatistics agentStatistics) {

        assertThat(Long.parseLong(indexAgentNumWithLevel1.getText()))
                .isEqualTo(agentStatistics.getAgentNumWithLevel());
        assertThat(Double.parseDouble(indexAgentCosts.getText()))
                .isEqualTo(agentStatistics.getAgentCosts());
        assertThat(Double.parseDouble(indexAgentCommission.getText()))
                .isEqualTo(agentStatistics.getAgentCommission());
        assertThat(Double.parseDouble(indexBalance.getText()))
                .isEqualTo(agentStatistics.getBalance());
        assertThat(Double.parseDouble(indexCommission.getText()))
                .isEqualTo(agentStatistics.getCommission());

    }
}
