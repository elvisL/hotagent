package com.huotu.hotagent.service.repository.log;

import com.huotu.hotagent.service.entity.log.BalanceLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by chendeyu on 2016/1/27.
 */
public interface BalanceLogRepository extends JpaRepository<BalanceLog, Long> {
}
