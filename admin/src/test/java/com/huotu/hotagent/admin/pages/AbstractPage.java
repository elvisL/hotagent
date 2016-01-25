/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin.pages;

import com.huotu.hotagent.admin.WebTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by allan on 1/25/16.
 */
public class AbstractPage {
    protected WebDriver webDriver;
    /**
     * 单元测试实例
     */
    protected WebTest testInstance;

    public AbstractPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void setTestInstance(WebTest testInstance) {
        this.testInstance = testInstance;
    }

    /**
     * 刷新页面
     */
    public void refresh() {
        webDriver.navigate().refresh();
        PageFactory.initElements(webDriver, this);
    }
}
