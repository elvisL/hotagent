package com.huotu.hotagent.service.service.record.impl;

import com.huotu.hotagent.common.constant.StringConstant;
import com.huotu.hotagent.common.constant.SysConstant;
import com.huotu.hotagent.common.ienum.EnumHelper;
import com.huotu.hotagent.common.utils.StringUtil;
import com.huotu.hotagent.service.common.AuditStatus;
import com.huotu.hotagent.service.entity.log.CommissionLog;
import com.huotu.hotagent.service.entity.record.WithdrawRecord;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.model.WithdrawSearch;
import com.huotu.hotagent.service.repository.log.CommissionLogRepository;
import com.huotu.hotagent.service.repository.record.WithdrawRepository;
import com.huotu.hotagent.service.service.record.WithdrawRecordService;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    @Override
    public List<WithdrawRecord> withdrawRecord(Long id) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        //将秒至0
        calendar.set(Calendar.SECOND,0);
        //将毫秒至0
        calendar.set(Calendar.MILLISECOND, 0);
        //获得当前月第一天
        Date sdate = calendar.getTime();
        //将当前月加1；
        calendar.add(Calendar.MONTH, 1);
        //在当前月的下一月基础上减去1毫秒
        calendar.add(Calendar.MILLISECOND, -1);
        //获得当前月最后一天
        Date edate = calendar.getTime();
        Specification<WithdrawRecord> specification = ((root,query,cb)->{
            List<Predicate> predicates = new ArrayList<>();
            AuditStatus auditStatus= EnumHelper.getEnumType(AuditStatus.class, 1);
            predicates.add(cb.equal(root.get("auditStatus"), auditStatus));
            if (!StringUtils.isEmpty(sdate)) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), sdate));
            }
            if (!StringUtils.isEmpty(edate)) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createTime").as(Date.class), edate));
            }
                predicates.add(cb.equal(root.get("agent").get("id").as(Long.class), id));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        });

        return withdrawRepository.findAll(specification);
    }

    @Override
    public Page<WithdrawRecord> findAll(int pageIndex, int pageSize, WithdrawSearch withdrawSearch) {

        Specification<WithdrawRecord> specification = ((root,query,cb)->{
            List<Predicate> predicates = new ArrayList<>();
            if (withdrawSearch.getAuditStatus() > -1) {
                AuditStatus auditStatus = EnumHelper.getEnumType(AuditStatus.class, withdrawSearch.getAuditStatus());
                predicates.add(cb.equal(root.get("auditStatus"), auditStatus));
            }
            if (!StringUtils.isEmpty(withdrawSearch.getBeginTime())) {
                Date beginDate = StringUtil.DateFormat(withdrawSearch.getBeginTime(), StringConstant.DATE_PATTERN);
                predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), beginDate));
            }
            if (!StringUtils.isEmpty(withdrawSearch.getEndTime())) {
                LocalDate endDate = LocalDate.parse(withdrawSearch.getEndTime(), DateTimeFormatter.ofPattern(StringConstant.DATE_PATTERN));
                endDate = endDate.plusDays(1);
                Date endTime = Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(endDate);
                predicates.add(cb.lessThan(root.get("createTime").as(Date.class), endTime));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        });
        return withdrawRepository.findAll(specification,new PageRequest(pageIndex - 1, pageSize, new Sort(Sort.Direction.DESC, "id")));
    }
}
