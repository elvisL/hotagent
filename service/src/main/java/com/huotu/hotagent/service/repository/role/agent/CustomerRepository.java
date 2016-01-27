/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.repository.role.agent;

import com.huotu.hotagent.service.entity.role.agent.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by chendeyu on 2016/1/25.
 */
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
