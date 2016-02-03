package com.huotu.hotagent.service.service.product.impl;

import com.huotu.hotagent.service.entity.product.Price;
import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.model.AgentProduct;
import com.huotu.hotagent.service.repository.product.PriceRepository;
import com.huotu.hotagent.service.service.product.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chendeyu on 2016/2/2.
 */
@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    PriceRepository priceRepository;


    @Override
    public List<AgentProduct> productList(List<Product> products, Long agentId) {
        List<AgentProduct> agentProductList = new ArrayList<>();
        for (Product product : products) {
            Price price =priceRepository.findByAgent_IdAndProduct_Id(agentId, product.getId());
            AgentProduct agentProduct = new AgentProduct();
            agentProduct.setProductId(product.getId());
            agentProduct.setProductName(product.getName());
            agentProduct.setProductPrice(price.getPrice());
            agentProductList.add(agentProduct);
        }
        return agentProductList;
    }
}
