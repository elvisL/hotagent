/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.common;

/**
 * Created by allan on 1/27/16.
 */
public enum Authority {
    MANAGER_ROOT("MANAGER_ROOT", "超级管理员"),
    MANAGER_AGENT("MANAGER_AGENT", "代理商管理"),
    MANAGER_PRODUCT("MANAGER_PRODUCT", "产品管理"),
    MANAGER_WITHDRAW("MANAGER_WITHDRAW", "提现管理"),
    AGENT_ROOT("AGENT_ROOT", "代理商");

    private String value;
    private String name;

    Authority(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
