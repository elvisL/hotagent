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
 * 产品类型
 * Created by cwb on 2016/1/22.
 */
public enum ProductType implements ICommonEnum {
    HUOBAN_MALL(0, "伙伴商城"),
    DSP(1, "DSP广告"),
    HOT_EDU(2, "云商学院"),
    THIRDPARTNAR(3, "水图代运营");
    // TODO: 1/25/16
    private int value;
    private String name;

    ProductType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
