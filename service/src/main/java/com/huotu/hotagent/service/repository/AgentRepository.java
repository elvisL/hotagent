package com.huotu.hotagent.service.repository;

import com.huotu.hotagent.service.entity.role.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by chendeyu on 2016/1/25.
 */
public interface AgentRepository extends JpaRepository<Agent,Long> {
}
