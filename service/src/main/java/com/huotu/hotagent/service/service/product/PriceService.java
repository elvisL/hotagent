package com.huotu.hotagent.service.service.product;

import com.huotu.hotagent.service.entity.product.Price;
import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.model.AgentProduct;
import com.huotu.hotagent.service.model.ProductPrice;

import java.util.List;
import java.util.Set;

/**
 * Created by chendeyu on 2016/2/2.
 */
public interface PriceService {
    List<AgentProduct> productList(List<Product> products,Long agentId);
    Set<Price> setProduct(Agent agent,ProductPrice productPrice);
    Set<Price> updateProduct(Agent agent,ProductPrice productPrice);



}
