/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.service.role.agent;

import com.huotu.hotagent.common.constant.ApiResult;
import com.huotu.hotagent.service.entity.role.agent.Customer;
import com.huotu.hotagent.service.model.CustomerSearch;
import org.springframework.data.domain.Page;

/**
 * Created by chendeyu on 2016/1/25.
 */
public interface CustomerService {

    Customer findById(Long id);

    Customer save(Customer customer);

    ApiResult addCustomer(Long id, Customer customer, int count);

    Page<Customer> findAll(int pageIndex, int pageSize,CustomerSearch customerSearch);
}
