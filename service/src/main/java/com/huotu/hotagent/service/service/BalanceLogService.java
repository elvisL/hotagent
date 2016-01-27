package com.huotu.hotagent.service.service;

import com.huotu.hotagent.common.constant.ApiResult;

/**
 * Created by chendeyu on 2016/1/27.
 */
public interface BalanceLogService {
    ApiResult importBl(Long id,double money);
}
