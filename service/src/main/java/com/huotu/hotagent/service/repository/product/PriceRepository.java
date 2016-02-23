package com.huotu.hotagent.service.repository.product;

import com.huotu.hotagent.service.common.ProductType;
import com.huotu.hotagent.service.entity.product.Price;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by chendeyu on 2016/1/25.
 */
public interface PriceRepository extends JpaRepository<Price,Long> {

    Price findByAgent_IdAndProduct_Id(Long agentId,Long productId);


}
