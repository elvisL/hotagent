package com.huotu.hotagent.service.repository;

import com.huotu.hotagent.service.entity.product.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by chendeyu on 2016/1/25.
 */
public interface PriceRepository extends JpaRepository<Price,Long> {

    List<Price> findByAgent_Id(Long id);
}
