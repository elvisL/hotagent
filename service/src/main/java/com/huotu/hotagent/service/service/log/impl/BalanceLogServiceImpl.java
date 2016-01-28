package com.huotu.hotagent.service.service.log.impl;

import com.huotu.hotagent.common.constant.ApiResult;
import com.huotu.hotagent.common.constant.ResultCodeEnum;
import com.huotu.hotagent.service.entity.log.BalanceLog;
import com.huotu.hotagent.service.entity.log.CommisionLog;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.repository.log.BalanceLogRepository;
import com.huotu.hotagent.service.repository.log.CommisionLogRepository;
import com.huotu.hotagent.service.service.log.BalanceLogService;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by chendeyu on 2016/1/27.
 */
public class BalanceLogServiceImpl implements BalanceLogService {

    @Autowired
    AgentService agentService;

    @Autowired
    BalanceLogRepository balanceLogRepository;

    @Autowired
    CommisionLogRepository commisionLogRepository;

    @Override
    @Transactional(value = "transactionManager")
    public ApiResult importBl(Long id, double money) {
        ApiResult apiResult =null;
        Date date = new Date();
        Agent lowAgent=agentService.findById(id);//二级代理
        Agent highAgent=lowAgent.getParent();
        if(highAgent.getBalance()-money>0) {//当一级代理商余额足够时
            lowAgent.setBalance(lowAgent.getBalance() + money);
            highAgent.setBalance(highAgent.getBalance() - money);
            BalanceLog lowbalanceLog = new BalanceLog();
            BalanceLog highbalanceLog = new BalanceLog();
            lowbalanceLog.setAgent(lowAgent);
            lowbalanceLog.setCreateTime(date);
            lowbalanceLog.setImportMoney(money);
            lowbalanceLog.setMoney(lowbalanceLog.getMoney()+money);
            highbalanceLog.setAgent(highAgent);
            highbalanceLog.setSupport(lowAgent);
            highbalanceLog.setCreateTime(date);
            highbalanceLog.setMoney(highbalanceLog.getMoney()-money);
            highbalanceLog.setExportMoney(money);
            balanceLogRepository.save(lowbalanceLog);
            balanceLogRepository.save(highbalanceLog);
        }
        else {
            if(highAgent.getBalance()+highAgent.getCommission()-money>0){//当余额+佣金大于充值金额时
                highAgent.setBalance(0);
                highAgent.setCommission(highAgent.getBalance()+highAgent.getCommission()-money);
                lowAgent.setBalance(lowAgent.getBalance()+money);
                BalanceLog lowbalanceLog = new BalanceLog();
                BalanceLog highbalanceLog = new BalanceLog();
                CommisionLog commisionLog = new CommisionLog();
                lowbalanceLog.setAgent(lowAgent);
                lowbalanceLog.setCreateTime(date);
                lowbalanceLog.setImportMoney(money);
                lowbalanceLog.setMoney(lowbalanceLog.getMoney()+money);
                highbalanceLog.setAgent(highAgent);
                highbalanceLog.setSupport(lowAgent);
                highbalanceLog.setCreateTime(date);
                highbalanceLog.setMoney(0);
                highbalanceLog.setExportMoney(highbalanceLog.getMoney());
                commisionLog.setAgent(highAgent);
                commisionLog.setSupport(lowAgent);
                commisionLog.setMoney(highAgent.getCommission()+highAgent.getBalance()-money);
                commisionLog.setExportMoney(money-highAgent.getBalance());
                commisionLog.setMoney(highAgent.getCommission()+highAgent.getBalance()-money);
                commisionLog.setCreateTime(date);
                balanceLogRepository.save(lowbalanceLog);
                balanceLogRepository.save(highbalanceLog);
                commisionLogRepository.save(commisionLog);
            }
            else {
                apiResult= ApiResult.resultWith(ResultCodeEnum.IMPORT_ERROR);
                return apiResult;
            }
        }
        agentService.save(lowAgent);
        agentService.save(highAgent);
        apiResult= ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        return apiResult;
    }
}
