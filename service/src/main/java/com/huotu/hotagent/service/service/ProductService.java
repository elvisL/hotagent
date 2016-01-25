package com.huotu.hotagent.service.service;

import com.huotu.hotagent.service.entity.product.Price;

import java.util.List;

/**
 * Created by chendeyu on 2016/1/25.
 */
public interface ProductService {

    List<Price> showProducts(Long id);
}
