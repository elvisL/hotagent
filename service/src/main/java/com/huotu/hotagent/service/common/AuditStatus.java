package com.huotu.hotagent.service.common;

/**
 * Created by Administrator on 2016/1/22.
 */
public enum AuditStatus {
    APPLYING(0,"申请中"),
    PROCESSED(1,"已审核");

    private int value;
    private String name;

    AuditStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
