/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.jsonmodel;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by allan on 1/29/16.
 */
@Setter
@Getter
public class ProPrice {
    private long productId;
    private String productName;
    private int productType;
    private double productPrice;
}
