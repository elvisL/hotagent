package com.huotu.hotagent.service.service.product;

import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.model.AgentProduct;

import java.util.List;

/**
 * Created by chendeyu on 2016/2/2.
 */
public interface PriceService {
    List<AgentProduct> productList(List<Product> products,Long agentId);
}
