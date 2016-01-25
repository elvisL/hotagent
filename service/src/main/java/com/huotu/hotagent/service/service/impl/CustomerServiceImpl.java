package com.huotu.hotagent.service.service.impl;

import com.huotu.hotagent.service.entity.role.Customer;
import com.huotu.hotagent.service.repository.CustomerRepository;
import com.huotu.hotagent.service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chendeyu on 2016/1/25.
 */

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;


    @Override
    public Customer findById(Long id) {
        Customer customer = customerRepository.findOne(id);
        return customer;
    }

    @Override
    public Customer save(Customer customer) {
        customerRepository.save(customer);
        return null;
    }
}
