package com.huotu.hotagent.admin.controller.withdraw;

import com.huotu.hotagent.common.constant.ApiResult;
import com.huotu.hotagent.common.constant.ResultCodeEnum;
import com.huotu.hotagent.common.constant.SysConstant;
import com.huotu.hotagent.common.utils.CommonUtils;
import com.huotu.hotagent.service.common.AuditStatus;
import com.huotu.hotagent.service.common.LogType;
import com.huotu.hotagent.service.entity.log.CommissionLog;
import com.huotu.hotagent.service.entity.record.WithdrawRecord;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.agent.Login;
import com.huotu.hotagent.service.service.log.CommissionLogService;
import com.huotu.hotagent.service.service.record.WithdrawRecordService;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 提现管理
 * Created by cwb on 2016/2/22.
 */
@Controller
public class WithdrawController {

    @Autowired
    private WithdrawRecordService withdrawRecordService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private CommissionLogService logService;

    /**
     * 提现申请列表
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "/withdraws",method = RequestMethod.GET)
    public ModelAndView showWithdrawRecords(@RequestParam(required = false,defaultValue = "1")int pageNo) {
        ModelAndView mav = new ModelAndView();
        Page<WithdrawRecord> withdrawRecords = withdrawRecordService.searchRecords(pageNo);
        int totalPages = withdrawRecords.getTotalPages();
        mav.addObject("records", withdrawRecords.getContent());
        mav.addObject("totalRecords", withdrawRecords.getTotalElements());
        mav.addObject("totalPages", totalPages);
        mav.addObject("currentPage", pageNo);
        mav.addObject("hasNext", withdrawRecords.hasNext());
        mav.addObject("hasPrevious", withdrawRecords.hasPrevious());
        mav.setViewName("withdraw/withdraw_list");
        int pageBtnNum = totalPages > SysConstant.DEFAULT_PAGE_BUTTON_NUM ? SysConstant.DEFAULT_PAGE_BUTTON_NUM : totalPages;
        int startPageNo = CommonUtils.calculateStartPageNo(pageNo, pageBtnNum, totalPages);
        List<Integer> pageNos = new ArrayList<>();
        for(int i=1;i<=pageBtnNum;i++) {
            pageNos.add(startPageNo);
            startPageNo++;
        }
        mav.addObject("pageNos",pageNos);
        return mav;
    }

    /**
     * 提现详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/withdraws/{id}",method = RequestMethod.GET)
    public ModelAndView showRecordDetail(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView();
        WithdrawRecord record = withdrawRecordService.getSpecifiedRecord(id);
        mv.addObject("record",record);
        mv.setViewName("withdraw/withdraw_detail");
        return mv;
    }

    /**
     * 更改提现状态
     * @param status
     * @return
     */
    @RequestMapping(value = "/withdraws/{id}/edit",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ApiResult changeAuditStatus(@PathVariable Long id,
                                       @RequestParam(required = false,defaultValue = "0") double commission,
                                       @RequestParam(required = false,defaultValue = "") String memo,
                                       AuditStatus status) {
        WithdrawRecord record = withdrawRecordService.getSpecifiedRecord(id);
        if(status==AuditStatus.FAIL) {
            Agent agent = record.getAgent();
            CommissionLog log = new CommissionLog();
            log.setCreateTime(new Date());
            log.setAgent(agent);
            log.setImportMoney(commission);
            log.setMoney(commission);
            agent.setCommission(agent.getCommission() + commission);
            record.setReply(memo);
            agentService.save(agent);
            log.setMemo(memo);
            log.setLogType(LogType.AUDIT);
            logService.save(log);
        }
        record.setAuditStatus(status);
        withdrawRecordService.save(record);
        return  ApiResult.resultWith(ResultCodeEnum.SUCCESS);
    }


}
