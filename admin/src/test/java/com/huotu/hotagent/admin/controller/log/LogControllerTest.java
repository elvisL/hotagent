package com.huotu.hotagent.admin.controller.log;

import com.huotu.hotagent.admin.common.AuthenticatedWebTest;
import com.huotu.hotagent.admin.common.LoginAs;
import com.huotu.hotagent.admin.controller.log.page.BalanceLogListPage;
import com.huotu.hotagent.service.entity.log.BalanceLog;
import com.huotu.hotagent.service.repository.log.BalanceLogRepository;
import com.huotu.hotagent.service.repository.log.CommissionLogRepository;
import com.huotu.hotagent.service.service.log.BalanceLogService;
import com.huotu.hotagent.service.service.log.CommissionLogService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by admin on 2016/2/25.
 */
public class LogControllerTest  extends AuthenticatedWebTest {
    @Autowired
    private BalanceLogService balanceLogService;
    @Autowired
    private BalanceLogRepository balanceLogRepository;
    @Autowired
    private CommissionLogService commissionLogService;
    @Autowired
    private CommissionLogRepository commissionLogRepository;

    @Before
    public void setUp() throws Exception {
        //随机构造一些余额日志?

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
        balanceLogListPage.validate();
    }

    @Test
    @LoginAs(isRoot = true)
    public void testCommissionLogList() throws Exception {

    }
}