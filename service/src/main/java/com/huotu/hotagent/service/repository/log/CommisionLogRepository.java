package com.huotu.hotagent.service.repository.log;

import com.huotu.hotagent.service.entity.log.CommisionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by chendeyu on 2016/1/28.
 */
public interface CommisionLogRepository extends JpaRepository<CommisionLog, Long> {
    List<CommisionLog> findByAgent_id(Long id);
}
