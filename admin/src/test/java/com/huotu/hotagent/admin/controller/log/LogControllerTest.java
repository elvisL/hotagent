package com.huotu.hotagent.admin.controller.log;

import com.huotu.hotagent.admin.common.AuthenticatedWebTest;
import com.huotu.hotagent.admin.common.LoginAs;
import com.huotu.hotagent.admin.controller.log.page.BalanceLogListPage;
import com.huotu.hotagent.admin.controller.log.page.CommissionLogListPage;
import com.huotu.hotagent.service.entity.log.BalanceLog;
import com.huotu.hotagent.service.entity.log.CommissionLog;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.repository.log.BalanceLogRepository;
import com.huotu.hotagent.service.repository.log.CommissionLogRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 2016/2/25.
 */
public class LogControllerTest  extends AuthenticatedWebTest {
    @Autowired
    private BalanceLogRepository balanceLogRepository;
    @Autowired
    private CommissionLogRepository commissionLogRepository;
    private List<Agent> saveAgent = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        int agentCount = random.nextInt(5) + 1;
        //随机构造一些代理商
        for (int i = 0; i < agentCount; i++) {
            Agent agent = agentService.save(mockAgent());
            saveAgent.add(agent);
        }
        int logCount = random.nextInt(50) + 1;
        //随机构造一些日志
        for (int i = 0; i < logCount; i++) {
            balanceLogRepository.save(mockBalanceLog());
            commissionLogRepository.save(mockCommissionLog());
        }

    }

    private CommissionLog mockCommissionLog() {
        CommissionLog commissionLog = new CommissionLog();
        int agentNo = random.nextInt(saveAgent.size());
        commissionLog.setAgent(saveAgent.get(agentNo));
        commissionLog.setCreateTime(new Date());
        commissionLog.setMoney(random.nextInt());
        commissionLog.setMemo(UUID.randomUUID().toString());
        commissionLog = commissionLogRepository.save(commissionLog);
        return commissionLog;
    }

    private BalanceLog mockBalanceLog() {
        BalanceLog balanceLog = new BalanceLog();
        int agentNo = random.nextInt(saveAgent.size());
        balanceLog.setAgent(saveAgent.get(agentNo));
        balanceLog.setCreateTime(new Date());
        balanceLog.setMoney(random.nextInt());
        balanceLog.setMemo(UUID.randomUUID().toString());
        balanceLog = balanceLogRepository.save(balanceLog);
        return balanceLog;
    }

    //1.分页显示余额日志，验证界面数据和查询数据库数据是否一致
    //2.根据条件查询
    @Test
    @LoginAs(isRoot = true)
    public void testBalanceLogList() throws Exception {
        webDriver.get("http://localhost/balanceLogs");
        BalanceLogListPage balanceLogListPage = initPage(BalanceLogListPage.class);
        List<BalanceLog> balanceLogs = balanceLogRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
        balanceLogListPage.setBalanceLogs(balanceLogs);
        balanceLogListPage.setMockAgent(saveAgent);
        balanceLogListPage.validate();
    }

    @Test
    @LoginAs(isRoot = true)
    public void testCommissionLogList() throws Exception {
        webDriver.get("http://localhost/commissionLogs");
        CommissionLogListPage commissionLogListPage = initPage(CommissionLogListPage.class);
        List<CommissionLog> commissionLogs = commissionLogRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
        commissionLogListPage.setCommissionLogs(commissionLogs);
        commissionLogListPage.setMockAgent(saveAgent);
        commissionLogListPage.validate();
    }
}