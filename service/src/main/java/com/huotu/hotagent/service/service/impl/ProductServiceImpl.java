package com.huotu.hotagent.service.service.impl;

import com.huotu.hotagent.service.entity.product.Price;
import com.huotu.hotagent.service.repository.product.PriceRepository;
import com.huotu.hotagent.service.service.ProductService;
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

    @Override
    public List<Price> showProducts(Long id) {
        List<Price> priceList= priceRepository.findByAgent_Id(id);
        return priceList;
    }
}
