/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.service.product;

import com.huotu.hotagent.service.common.ProductType;
import com.huotu.hotagent.service.entity.product.Product;

import java.util.List;

/**
 * 产品
 * Created by chendeyu on 2016/1/25.
 */
public interface ProductService {

//    List<Price> showProducts(Long id);

    Product findOne(Long id);

    Product findByProductTypeAndParent(ProductType productType,Product product);

    List<Product> findByParentId(Long id);




    Product save(Product product);

    boolean exists();

    Product update(Product product);

    List<Product> findAll();

    List<Product> findTops();

    List<Product> findSubs();
}
