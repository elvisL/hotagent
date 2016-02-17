/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.common;

import com.huotu.hotagent.common.ienum.ICommonEnum;

/**
 * 代理商类型
 * Created by cwb on 2016/1/21.
 */
public enum AgentType implements ICommonEnum{
    NORMAL(0,"普通代理"),
    SOLE(1,"独家代理");

    private Integer value;
    private String name;
    AgentType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static AgentType getAgentType(Integer value)
    {
        switch (value){
            case 0:
                return NORMAL;
            case 1:
                return SOLE;
            default:
                return null;
        }
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
