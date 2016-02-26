package com.huotu.hotagent.service.repository.log;

import com.huotu.hotagent.service.entity.log.CommissionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by chendeyu on 2016/1/28.
 */
@Repository
public interface CommissionLogRepository extends JpaRepository<CommissionLog, Long>,JpaSpecificationExecutor {
    List<CommissionLog> findByAgent_id(Long id);
}
