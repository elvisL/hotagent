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
import com.huotu.hotagent.service.entity.log.CommissionLog;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.agent.Customer;
import com.huotu.hotagent.service.repository.log.BalanceLogRepository;
import com.huotu.hotagent.service.repository.log.CommissionLogRepository;
import com.huotu.hotagent.service.repository.product.PriceRepository;
import com.huotu.hotagent.service.repository.product.ProductRepository;
import com.huotu.hotagent.service.repository.role.agent.AgentRepository;
import com.huotu.hotagent.service.repository.role.agent.CustomerRepository;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import com.huotu.hotagent.service.service.role.agent.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by chendeyu on 2016/1/25.
 */

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private  AgentService agentService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BalanceLogRepository balanceLogRepository;

    @Autowired
    private CommissionLogRepository commissionLogRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private AgentRepository agentRepository;


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
    @Transactional(value = "transactionManager")
    public ApiResult addCustomer(Long id, Customer customer, int count ) {
        ApiResult apiResult = null;
        Agent agent = agentService.findById(id);
        Date date = new Date();
        Double price =priceRepository.findByAgent_IdAndProduct_Id(agent.getId(),customer.getProductId()).getPrice();//代理商每件产品的价格
        Double money = count*price;
        if(agent.getBalance()-money>=0) {//当一级代理商余额足够时
            BalanceLog highbalanceLog = new BalanceLog();
            highbalanceLog.setAgent(agent);
            highbalanceLog.setCustomer(customer);
            highbalanceLog.setCreateTime(date);
            highbalanceLog.setMoney(-money);
            highbalanceLog.setExportMoney(money);
            highbalanceLog.setMemo(customer.getName()+" 向 "+agent.getName()+" 购买产品 "+productRepository.findOne(customer.getProductId()).getName()+" 花费余额 "+money );
            agent.setBalance(agent.getBalance() - money);
            agentRepository.save(agent);
            customerRepository.save(customer);
            balanceLogRepository.save(highbalanceLog);
        }
        else {
            if(agent.getBalance()+agent.getCommission()-money>=0){//当余额+佣金大于充值金额时

                BalanceLog highbalanceLog = new BalanceLog();
                CommissionLog commissionLog = new CommissionLog();
                highbalanceLog.setAgent(agent);
                highbalanceLog.setCreateTime(date);
                highbalanceLog.setCustomer(customer);
                highbalanceLog.setMoney(-agent.getBalance());
                highbalanceLog.setExportMoney(agent.getBalance());
                highbalanceLog.setMemo(customer.getName()+" 向 "+agent.getName()+" 购买产品 "+productRepository.findOne(customer.getProductId()).getName()+" 花费余额 "+agent.getBalance() );
                commissionLog.setAgent(agent);
                commissionLog.setCustomer(customer);
                commissionLog.setMoney(agent.getBalance()-money);
                commissionLog.setExportMoney(money-agent.getBalance());
                commissionLog.setMemo(customer.getName()+" 向 "+agent.getName()+" 购买产品 "+productRepository.findOne(customer.getProductId()).getName()+" 花费佣金 "+commissionLog.getExportMoney() );
                commissionLog.setCreateTime(date);
                agent.setCommission(agent.getBalance() + agent.getCommission() - money);
                agent.setBalance(0);
                agentRepository.save(agent);
                customerRepository.save(customer);
                balanceLogRepository.save(highbalanceLog);
                commissionLogRepository.save(commissionLog);
            }
            else {
                apiResult= ApiResult.resultWith(ResultCodeEnum.IMPORT_ERROR);
                return apiResult;
            }
        }
        //返佣处理
        if (agent.getLevel().getLevel()==1){
            Agent lowAgent = agentService.findById(id);
            Agent highagent = agent.getParent();
            Double highAgentPrice =priceRepository.findByAgent_IdAndProduct_Id(highagent.getId(),customer.getProductId()).getPrice();//上级代理商每件产品的价格
            //返佣为代理产品价格的差额
            Double commission = money-count*highAgentPrice;
            highagent.setCommission(highagent.getCommission()+commission);
            CommissionLog commissionLog = new CommissionLog();
            commissionLog.setAgent(highagent);
            commissionLog.setMoney(commission);
            commissionLog.setImportMoney(commission);
            commissionLog.setMemo("二级代理商 "+lowAgent.getName()+ "向 "+highagent.getName()+" 返佣 "+commission );
            commissionLog.setCreateTime(date);
            commissionLogRepository.save(commissionLog);
            agentRepository.save(agent);

        }
        apiResult= ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        return apiResult;
    }
}
