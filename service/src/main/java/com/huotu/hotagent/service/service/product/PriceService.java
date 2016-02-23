package com.huotu.hotagent.service.service.product;

import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.model.AgentProduct;
import com.huotu.hotagent.service.model.ProductPrice;

import java.util.List;

/**
 * Created by chendeyu on 2016/2/2.
 */
public interface PriceService {
    List<AgentProduct> productList(List<Product> products,Long agentId);
    Boolean setProduct(Agent agent,ProductPrice productPrice);
}
