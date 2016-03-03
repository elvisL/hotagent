package com.huotu.hotagent.agent.controller;

import com.huotu.hotagent.common.constant.ApiResult;
import com.huotu.hotagent.common.constant.ResultCodeEnum;
import com.huotu.hotagent.common.constant.SysConstant;
import com.huotu.hotagent.service.entity.record.WithdrawRecord;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.model.WithdrawSearch;
import com.huotu.hotagent.service.service.record.WithdrawRecordService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by chendeyu on 2016/2/25.
 */

@Controller
public class WithdrawController {

    private static final Log log = LogFactory.getLog(WithdrawController.class);

    @Autowired
    WithdrawRecordService withdrawRecordService;

    /**
     *代理商提现(页面)
     */
    @PreAuthorize("hasAnyAuthority('AGENT_ROOT')")
    @RequestMapping(value = "/withdraw",method = RequestMethod.GET)
    public ModelAndView withdraw(@AuthenticationPrincipal Agent agent) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/views/withdraw/withdraw");
        modelAndView.addObject("commission",agent.getCommission());
        return  modelAndView;
    }

    /**
     * 提现申请列表
     * @param pageNo
     * @return
     */
    @PreAuthorize("hasAnyAuthority('AGENT_ROOT')")
    @RequestMapping(value = "/withdrawList",method = RequestMethod.GET)
    public ModelAndView withdrawList(@AuthenticationPrincipal Agent agent,@RequestParam(required = false, defaultValue = "1") int pageNo,WithdrawSearch withdrawSearch) {
        ModelAndView modelAndView =  new ModelAndView();
        withdrawSearch.setAgentId(agent.getId());
        Page<WithdrawRecord> withdrawRecords = withdrawRecordService.findAll(pageNo, SysConstant.DEFAULT_PAGE_SIZE, withdrawSearch);
        modelAndView.addObject("withdrawSearch", withdrawSearch);
        modelAndView.addObject("pageSize", withdrawRecords.getSize());
        modelAndView.addObject("withdrawRecords", withdrawRecords.getContent());
        modelAndView.addObject("totalRecords", withdrawRecords.getTotalElements());
        modelAndView.addObject("totalPages", withdrawRecords.getTotalPages());
        modelAndView.addObject("currentPage", pageNo);
        modelAndView.setViewName("/views/withdraw/withdraw_list");
        return modelAndView;
    }



    /**
     *代理商提现申请
     */
    @RequestMapping(value = "/applyWithdraw",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult applyWithdraw(@AuthenticationPrincipal Agent agent,double money,String message) throws Exception{
        ApiResult apiResult = null;
        try {
            if (withdrawRecordService.withdrawRecord(agent.getId()).size()>=3){//如果本月提现成功提现次数大于3
                apiResult =  ApiResult.resultWith(ResultCodeEnum.WITHDRAW_ERROR);
                return apiResult;
            }
            else {
                Boolean bl = withdrawRecordService.withdraw(agent, money, message);
                if (bl == true) {
                    apiResult = ApiResult.resultWith(ResultCodeEnum.SUCCESS);
                } else {
                    apiResult = ApiResult.resultWith(ResultCodeEnum.IMPORT_ERROR);
                }
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
            apiResult = ApiResult.resultWith(ResultCodeEnum.SAVE_DATA_ERROR);
        }
        return apiResult;

    }


}
