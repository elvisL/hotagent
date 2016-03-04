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
 * Created by Administrator on 2016/1/22.
 */
public enum AuditStatus implements ICommonEnum {

    APPLYING(0,"申请中"),
    PROCESSED(1,"通过"),
    FAIL(2,"失败");

    private int value;
    private String name;

    AuditStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static AuditStatus getAuditStatus(int value) {
        switch (value) {
            case 0:
                return APPLYING;
            case 1:
                return PROCESSED;
            case 2:
                return FAIL;
            default:
                return null;
        }
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Object getName() {
        return name;
    }

}
