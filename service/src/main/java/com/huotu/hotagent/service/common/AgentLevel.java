package com.huotu.hotagent.service.common;

/**
 * Created by Administrator on 2016/1/21.
 */
public enum AgentLevel {
    ONE(0,"一级代理"),
    TWO(1,"二级代理");

    AgentLevel(int value, String name) {
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
