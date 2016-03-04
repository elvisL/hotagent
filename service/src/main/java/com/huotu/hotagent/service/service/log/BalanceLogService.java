package com.huotu.hotagent.service.service.log;

import com.huotu.hotagent.service.entity.log.BalanceLog;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.model.LogSearch;
import org.springframework.data.domain.Page;

/**
 * Created by chendeyu on 2016/1/27.
 */
public interface BalanceLogService {
    Boolean importBl(Agent lowAgent, double money);

    Page<BalanceLog> findAll(int pageIndex, int pageSize, LogSearch logSearch);
    void save(BalanceLog balanceLog);
}
