package com.huotu.hotagent.service.repository.product;

import com.huotu.hotagent.service.entity.product.Price;
import com.huotu.hotagent.service.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by chendeyu on 2016/1/25.
 */
public interface PriceRepository extends JpaRepository<Price,Long> {

    Price findByAgent_IdAndProduct_Id(Long agentId,Long productId);

    List<Price> findByAgent_Id(Long id);


    List<Price> findByProduct(Product product);
}
