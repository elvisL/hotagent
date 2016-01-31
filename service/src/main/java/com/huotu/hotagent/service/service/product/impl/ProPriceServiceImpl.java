/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.service.product.impl;

import com.alibaba.fastjson.JSON;
import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.jsonmodel.ProPrice;
import com.huotu.hotagent.service.service.product.ProPriceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allan on 1/29/16.
 */
@Service
public class ProPriceServiceImpl implements ProPriceService {

    @Override
    public String getPriceSerial(List<Product> products) {
        List<ProPrice> proPrices = new ArrayList<>();
        for (Product product : products) {
            ProPrice proPrice = new ProPrice();
            proPrice.setProductId(product.getId());
            proPrice.setProductName(product.getName());
            proPrice.setProductType(product.getProductType().getValue());
            proPrice.setProductPrice(product.getBasePrice());
            proPrices.add(proPrice);
        }
        return JSON.toJSONString(proPrices);
    }
}
