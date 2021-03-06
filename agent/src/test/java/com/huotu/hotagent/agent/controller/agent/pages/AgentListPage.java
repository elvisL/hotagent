/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.agent.controller.agent.pages;

import com.huotu.hotagent.agent.pages.AbstractPage;
import com.huotu.hotagent.common.constant.SysConstant;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.agent.AgentLevel;
import com.huotu.hotagent.service.model.AgentSearch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by allan on 2/15/16.
 */
public class AgentListPage extends AbstractPage {
    @FindBy(css = "table[class~=table-striped]")
    private WebElement agentsTable;
    @FindBy(css = ".pagination li")
    private List<WebElement> pageBtn;
    @FindBy(css = "input[name=agentName]")
    private WebElement agentName;
    @FindBy(css = "input[name=pageIndex]")
    private WebElement pageIndex;
    @FindBy(css = "input[name=beginTime]")
    private WebElement beginTime;
    @FindBy(css = "input[name=endTime]")
    private WebElement endTime;
    @FindBy(css = "input[name=agentType]")
    private WebElement agentType;
//    @FindBy(css = "input[name=agentLevel]")
//    private WebElement agentLevel;
    @FindBy(className = "submitSearch")
    private WebElement submitSearch;
    @FindBy(className = "resetSearch")
    private WebElement resetSearch;

    private List<Agent> agents;
    private List<AgentLevel> agentLevels;


    public AgentListPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void validate() {
        assertThat(pageIndex.getAttribute("value")).isEqualTo("1");
        AgentSearch agentSearch = new AgentSearch();
        agentSearch.setAgentLevel(1);
        validateSearchResult(agentSearch);
    }

    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }

    public void setAgentLevels(List<AgentLevel> agentLevels) {
        this.agentLevels = agentLevels;
    }

    public void testPaging() {

    }

    public void testSearch(AgentSearch randomSearch) {
        webDriver.findElement(By.id("typeSelectBtn")).click();
        List<WebElement> typeItems = webDriver.findElements(By.cssSelector("#typeSelect a"));
        WebElement chosenType = typeItems.stream().filter(item -> item.getAttribute("type-value").equals(String.valueOf(randomSearch.getAgentType()))).findFirst().get();
        chosenType.click();

        submitSearch.click();

        validateSearchResult(randomSearch);
    }


    public void validateSearchResult(AgentSearch randomSearch) {
        long itemSize = agentsTable.findElements(By.tagName("tr")).size() - 1;
        long pageBtnSize = pageBtn.size() - 4;

        long currentLevel = randomSearch.getAgentLevel() == -1 ? 0
                : agentLevels.stream().filter(level -> level.getLevel() == randomSearch.getAgentLevel())
                .findFirst().get().getLevel();

        long resultCount = agents.stream().filter(agent ->
                agent.getLevel().getLevel() == currentLevel
        ).count();
        if (randomSearch.getAgentType() > -1) {
            resultCount = agents.stream().filter(agent ->
                    agent.getLevel().getLevel() == currentLevel
                            && agent.getType().getValue() == randomSearch.getAgentType()
            ).count();
        }
        long pageCount = resultCount % SysConstant.DEFAULT_PAGE_SIZE == 0 ? resultCount / SysConstant.DEFAULT_PAGE_SIZE : resultCount / SysConstant.DEFAULT_PAGE_SIZE + 1;

        if (resultCount <= SysConstant.DEFAULT_PAGE_SIZE) {
            assertThat(itemSize)
                    .isEqualTo(resultCount);
            assertThat(pageBtnSize)
                    .isEqualTo(pageCount);
        } else {
            assertThat(itemSize)
                    .isEqualTo(SysConstant.DEFAULT_PAGE_SIZE);
            assertThat(pageBtnSize)
                    .isEqualTo(pageCount > 8 ? 8 : pageCount);
        }
    }
}
