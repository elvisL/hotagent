package com.huotu.hotagent.service.service.log.impl;

import com.huotu.hotagent.common.constant.StringConstant;
import com.huotu.hotagent.common.utils.StringUtil;
import com.huotu.hotagent.service.entity.log.BalanceLog;
import com.huotu.hotagent.service.entity.log.CommissionLog;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.model.LogSearch;
import com.huotu.hotagent.service.repository.log.BalanceLogRepository;
import com.huotu.hotagent.service.repository.log.CommissionLogRepository;
import com.huotu.hotagent.service.service.log.BalanceLogService;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chendeyu on 2016/1/27.
 */
@Service
public class BalanceLogServiceImpl implements BalanceLogService {

    @Autowired
    AgentService agentService;

    @Autowired
    BalanceLogRepository balanceLogRepository;

    @Autowired
    CommissionLogRepository commissionLogRepository;

    @Override
    @Transactional(value = "transactionManager")
    public Boolean importBl(Agent lowAgent, double money) {
        Date date = new Date();
        Agent highAgent = lowAgent.getParent();
        if (highAgent.getBalance() - money > 0) {//当一级代理商余额足够时
            lowAgent.setBalance(lowAgent.getBalance() + money);
            highAgent.setBalance(highAgent.getBalance() - money);
            BalanceLog lowbalanceLog = new BalanceLog();
            BalanceLog highbalanceLog = new BalanceLog();
            lowbalanceLog.setAgent(lowAgent);
            lowbalanceLog.setCreateTime(date);
            lowbalanceLog.setImportMoney(money);
            lowbalanceLog.setMoney(money);
            lowbalanceLog.setMemo("一级代理商 "+highAgent.getName()+" 向您充值 "+money);
            highbalanceLog.setAgent(highAgent);
            highbalanceLog.setSupport(lowAgent);
            highbalanceLog.setCreateTime(date);
            highbalanceLog.setMoney(-money);
            highbalanceLog.setExportMoney(money);
            highbalanceLog.setMemo("您向二级代理商 "+lowAgent.getName()+" 充值 "+money);
            balanceLogRepository.save(lowbalanceLog);
            balanceLogRepository.save(highbalanceLog);
        } else {
            if (highAgent.getBalance() + highAgent.getCommission() - money > 0) {//当余额+佣金大于充值金额时
                BalanceLog lowbalanceLog = new BalanceLog();
                BalanceLog highbalanceLog = new BalanceLog();
                CommissionLog commissionLog = new CommissionLog();
                lowbalanceLog.setAgent(lowAgent);
                lowbalanceLog.setCreateTime(date);
                lowbalanceLog.setImportMoney(money);
                lowbalanceLog.setMoney(money);
                lowbalanceLog.setMemo("一级代理商 "+highAgent.getName()+" 向您充值 "+money);
                highbalanceLog.setAgent(highAgent);
                highbalanceLog.setSupport(lowAgent);
                highbalanceLog.setCreateTime(date);
                highbalanceLog.setExportMoney(highAgent.getBalance());
                highbalanceLog.setMoney(-highAgent.getBalance());
                highbalanceLog.setMemo("您向二级代理商 "+lowAgent.getName()+" 充值 "+highAgent.getBalance());
                commissionLog.setAgent(highAgent);
                commissionLog.setSupport(lowAgent);
                commissionLog.setMoney(highAgent.getBalance()-money);
                commissionLog.setExportMoney(money - highAgent.getBalance());
                commissionLog.setCreateTime(date);
                commissionLog.setMemo("您向二级代理商 "+lowAgent.getName()+" 充值 "+commissionLog.getExportMoney());
                highAgent.setCommission(highAgent.getBalance() + highAgent.getCommission() - money);
                highAgent.setBalance(0);
                lowAgent.setBalance(lowAgent.getBalance() + money);
                balanceLogRepository.save(lowbalanceLog);
                balanceLogRepository.save(highbalanceLog);
                commissionLogRepository.save(commissionLog);
            } else {
                return false;
            }
        }
        agentService.save(lowAgent);
        agentService.save(highAgent);
        return true;
    }

    @Override
    public Page<BalanceLog> findAll(int pageIndex, int pageSize, LogSearch logSearch) {
        Specification<BalanceLog> specification = ((root,query,cb)->{
            List<Predicate> predicates = new ArrayList<>();
            if(!StringUtils.isEmpty(logSearch.getAgentName())){
                predicates.add(cb.like(root.get("agent").get("name").as(String.class),"%" + logSearch.getAgentName() + "%"));
            }
            /*if(!StringUtils.isEmpty(logSearch.getSupportName())){
                predicates.add(cb.like(root.get("support").get("name").as(String.class),"%" + logSearch.getSupportName() + "%"));
            }*/
            if(!StringUtils.isEmpty(logSearch.getCustomerName())){
                predicates.add(cb.like(root.get("customer").get("name").as(String.class),"%" + logSearch.getCustomerName() + "%"));
            }
            if (!StringUtils.isEmpty(logSearch.getBeginTime())) {
                Date beginDate = StringUtil.DateFormat(logSearch.getBeginTime(), StringConstant.DATE_PATTERN);
                predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), beginDate));
            }
            if (!StringUtils.isEmpty(logSearch.getEndTime())) {
                LocalDate endDate = LocalDate.parse(logSearch.getEndTime(), DateTimeFormatter.ofPattern(StringConstant.DATE_PATTERN));
                endDate = endDate.plusDays(1);
                Date endTime = Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(endDate);
                predicates.add(cb.lessThan(root.get("createTime").as(Date.class), endTime));
            }
            if(logSearch.getAgent() > 0){
                predicates.add(cb.equal(root.get("agent").get("id").as(Integer.class), logSearch.getAgent()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        });
        return balanceLogRepository.findAll(specification,new PageRequest(pageIndex - 1, pageSize, new Sort(Sort.Direction.DESC, "id")));
    }
}
