package com.huotu.hotagent.service.common;

/**
 * 代理商类型
 * Created by cwb on 2016/1/21.
 */
public enum AgentType {
    NORMAL(0,"普通代理"),
    SOLE(1,"独家代理");

    AgentType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    private int value;
    private String name;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
