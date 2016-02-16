/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.service.role.agent.impl;

import com.huotu.hotagent.common.constant.ApiResult;
import com.huotu.hotagent.common.constant.ResultCodeEnum;
import com.huotu.hotagent.service.entity.log.BalanceLog;
import com.huotu.hotagent.service.entity.log.CommisionLog;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.agent.Customer;
import com.huotu.hotagent.service.repository.log.BalanceLogRepository;
import com.huotu.hotagent.service.repository.log.CommisionLogRepository;
import com.huotu.hotagent.service.repository.role.agent.AgentRepository;
import com.huotu.hotagent.service.repository.role.agent.CustomerRepository;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import com.huotu.hotagent.service.service.role.agent.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by chendeyu on 2016/1/25.
 */

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    AgentService agentService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    BalanceLogRepository balanceLogRepository;

    @Autowired
    CommisionLogRepository commisionLogRepository;

    @Autowired
    AgentRepository agentRepository;


    @Override
    public Customer findById(Long id) {
        Customer customer = customerRepository.findOne(id);
        return customer;
    }

    @Override
    public Customer save(Customer customer) {
        customerRepository.save(customer);
        return null;
    }

    @Override

    public ApiResult addCustomer(Long id, Customer customer, int money) {
        ApiResult apiResult = null;
        Agent highAgent = agentService.findById(id);
        Date date = new Date();
        if(highAgent.getBalance()-money>0) {//当一级代理商余额足够时
            highAgent.setBalance(highAgent.getBalance() - money);
            BalanceLog highbalanceLog = new BalanceLog();
            highbalanceLog.setAgent(highAgent);
            highbalanceLog.setCustomer(customer);
            highbalanceLog.setCreateTime(date);
            highbalanceLog.setMoney(money);
            highbalanceLog.setExportMoney(money);
            agentRepository.save(highAgent);
            customerRepository.save(customer);
            balanceLogRepository.save(highbalanceLog);
        }
        else {
            if(highAgent.getBalance()+highAgent.getCommission()-money>0){//当余额+佣金大于充值金额时
                highAgent.setBalance(0);
                highAgent.setCommission(highAgent.getBalance()+highAgent.getCommission()-money);
                BalanceLog highbalanceLog = new BalanceLog();
                CommisionLog commisionLog = new CommisionLog();
                highbalanceLog.setAgent(highAgent);
                highbalanceLog.setCreateTime(date);
                highbalanceLog.setMoney(0);
                highbalanceLog.setExportMoney(highbalanceLog.getMoney());
                commisionLog.setAgent(highAgent);
                commisionLog.setCustomer(customer);
                commisionLog.setMoney(highAgent.getCommission()+highAgent.getBalance()-money);
                commisionLog.setExportMoney(money-highAgent.getBalance());
                commisionLog.setMoney(highAgent.getCommission()+highAgent.getBalance()-money);
                commisionLog.setCreateTime(date);
                agentRepository.save(highAgent);
                customerRepository.save(customer);
                balanceLogRepository.save(highbalanceLog);
                commisionLogRepository.save(commisionLog);
            }
            else {
                apiResult= ApiResult.resultWith(ResultCodeEnum.IMPORT_ERROR);
                return apiResult;
            }
        }
//        agentService.save(highAgent);
        apiResult= ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        return apiResult;
    }
}
