package com.huotu.hotagent.admin.controller.log;

import com.huotu.hotagent.common.constant.SysConstant;
import com.huotu.hotagent.service.entity.log.BalanceLog;
import com.huotu.hotagent.service.entity.log.CommissionLog;
import com.huotu.hotagent.service.model.LogSearch;
import com.huotu.hotagent.service.service.log.BalanceLogService;
import com.huotu.hotagent.service.service.log.CommissionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by admin on 2016/2/23.
 */
@Controller
public class LogController {

    @Autowired
    private BalanceLogService balanceLogService;
    @Autowired
    private CommissionLogService commissionLogService;

    /**
     * 余额日志
     */
    @RequestMapping(value = "/balanceLogs", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('MANAGER_ROOT','MANAGER_AGENT')")
    public String balanceLogList(@RequestParam(required = false, defaultValue = "1") int pageNo,LogSearch logSearch,
                                 Model model) {
        Page<BalanceLog> balanceLogs = balanceLogService.findAll(pageNo, SysConstant.DEFAULT_PAGE_SIZE, logSearch);
        model.addAttribute("pageSize",balanceLogs.getSize());
        model.addAttribute("balanceLogs",balanceLogs.getContent());
        model.addAttribute("totalRecords",balanceLogs.getTotalElements());
        model.addAttribute("totalPages",balanceLogs.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        return "log/balanceLog_list";
    }

    /**
     * 佣金日志
     */
    @RequestMapping(value = "/commissionLogs", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('MANAGER_ROOT','MANAGER_AGENT')")
    public String commissionLogList(@RequestParam(required = false, defaultValue = "1") int pageNo,LogSearch logSearch,
                                 Model model) {
        Page<CommissionLog> commissionLogs = commissionLogService.findAll(pageNo, SysConstant.DEFAULT_PAGE_SIZE, logSearch);
        model.addAttribute("pageSize",commissionLogs.getSize());
        model.addAttribute("commissionLogs",commissionLogs.getContent());
        model.addAttribute("totalRecords",commissionLogs.getTotalElements());
        model.addAttribute("totalPages",commissionLogs.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        return "log/commissionLog_list";
    }
}
