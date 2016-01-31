/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.repository.record;

import com.huotu.hotagent.service.common.AuditStatus;
import com.huotu.hotagent.service.entity.record.WithdrawRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by allan on 1/30/16.
 */
public interface WithdrawRepository extends JpaRepository<WithdrawRecord, Long> {

    long countByAuditStatus(AuditStatus auditStatus);
}
