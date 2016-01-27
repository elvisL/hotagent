/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.service.role.manager;

import com.huotu.hotagent.service.entity.role.manager.Manager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by allan on 1/27/16.
 */
public interface ManagerService extends UserDetailsService {
    @Transactional
    Manager save(Manager manager);

    Manager findByUsername(String username);
}
