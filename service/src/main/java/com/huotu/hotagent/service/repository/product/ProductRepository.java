/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.repository.product;

import com.huotu.hotagent.service.common.ProductType;
import com.huotu.hotagent.service.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by allan on 1/25/16.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByProductTypeAndParent(ProductType productType,Product product);

    Product findByProductType(ProductType productType);

    List<Product> findByParent_Id(Long id);

    List<Product> findByParent(Product parent);

    List<Product> findByChildren(List<Product> children);
}
