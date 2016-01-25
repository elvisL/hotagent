package com.huotu.hotagent.service.service;

import com.huotu.hotagent.service.entity.role.Customer;

/**
 * Created by chendeyu on 2016/1/25.
 */
public interface CustomerService {

    Customer findById(Long id);

    Customer save(Customer customer);
}
