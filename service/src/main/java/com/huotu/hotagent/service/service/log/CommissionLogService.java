package com.huotu.hotagent.service.service.log;

import com.huotu.hotagent.service.entity.log.CommissionLog;
import com.huotu.hotagent.service.model.LogSearch;
import org.springframework.data.domain.Page;

/**
 * Created by admin on 2016/2/24.
 */
public interface CommissionLogService {
    Page<CommissionLog> findAll(int pageIndex, int pageSize, LogSearch logSearch);

    void save(CommissionLog log);
}
