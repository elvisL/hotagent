/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.service.product.impl;

import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.repository.product.PriceRepository;
import com.huotu.hotagent.service.repository.product.ProductRepository;
import com.huotu.hotagent.service.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chendeyu on 2016/1/25.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    private ProductRepository productRepository;

//    @Override
//    public List<Price> showProducts(Long id) {
//        List<Price> priceList = priceRepository.findByAgent_Id(id);
//        return priceList;
//    }

    @Override
    public Product findOne(Long id) {
        Product product =  productRepository.findOne(id);
        return  product;
    }

    @Override
    public List<Product> findByParentId(Long id) {
        List<Product>  productList = productRepository.findByParent_Id(id) ;
        return productList;
    }


    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public boolean exists() {
        return productRepository.count() > 0;
    }

    @Override
    public Product update(Product product) {
        //如果ID为空，则返回null(产品不能新增)；否则修改产品
        Product old;
        if(product.getId() == null){
            return null;
        }else{
            old = productRepository.findOne(product.getId());
            if(old == null){
                return null;
            }
            old.setName(product.getName());
            old.setBasePrice(product.getBasePrice());
            old.setProductDesc(product.getProductDesc());
            old = productRepository.save(old);
        }
        return old;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findTops() {
        return productRepository.findByParent(null);
    }
}
