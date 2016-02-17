package com.huotu.hotagent.service.service.log;

import com.huotu.hotagent.service.entity.role.agent.Agent;

/**
 * Created by chendeyu on 2016/1/27.
 */
public interface BalanceLogService {
    Boolean importBl(Agent lowAgent,double money);
}
