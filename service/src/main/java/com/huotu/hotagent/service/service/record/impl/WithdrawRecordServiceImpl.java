package com.huotu.hotagent.service.service.record.impl;

import com.huotu.hotagent.common.constant.SysConstant;
import com.huotu.hotagent.service.common.AuditStatus;
import com.huotu.hotagent.service.entity.log.CommissionLog;
import com.huotu.hotagent.service.entity.record.WithdrawRecord;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.repository.log.CommissionLogRepository;
import com.huotu.hotagent.service.repository.record.WithdrawRepository;
import com.huotu.hotagent.service.service.record.WithdrawRecordService;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 提现管理
 * Created by cwb on 2016/2/22.
 */
@Service
public class WithdrawRecordServiceImpl implements WithdrawRecordService {

    @Autowired
    private WithdrawRepository withdrawRepository;
    @Autowired
    private AgentService agentService;

    @Autowired
    private CommissionLogRepository commissionLogRepository;

    @Override
    public Page<WithdrawRecord> searchRecords(int pageNo) {
        return withdrawRepository.findAll(new PageRequest(pageNo-1, SysConstant.DEFAULT_PAGE_SIZE,new Sort(Sort.Direction.ASC,"createTime")));
    }

    @Override
    public WithdrawRecord getSpecifiedRecord(Long id) {
        return withdrawRepository.findOne(id);
    }

    @Override
    public void save(WithdrawRecord record) {
        withdrawRepository.save(record);
    }

    @Override
    public Boolean withdraw(Agent agent, double money ,String message) {
        agent.setCommission(agent.getCommission()-money);
        CommissionLog commissionLog = new CommissionLog();
        commissionLog.setCreateTime(new Date());
        commissionLog.setExportMoney(money);
        commissionLog.setMoney(-money);
        commissionLog.setAgent(agent);
        commissionLog.setMemo(agent.getName()+" 申请提现佣金 "+money);
        WithdrawRecord withdrawRecord = new WithdrawRecord();
        withdrawRecord.setMoney(money);
        withdrawRecord.setAgent(agent);
        withdrawRecord.setCreateTime(new Date());
        withdrawRecord.setAuditStatus(AuditStatus.APPLYING);
        withdrawRecord.setMessage(message);
        withdrawRepository.save(withdrawRecord);
        agentService.save(agent);
        commissionLogRepository.save(commissionLog);
        return true;
    }
}
