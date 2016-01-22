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
 * Created by Administrator on 2016/1/22.
 */
public enum AuditStatus {
    APPLYING(0,"申请中"),
    PROCESSED(1,"已体现");

    private int value;
    private String name;

    AuditStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
