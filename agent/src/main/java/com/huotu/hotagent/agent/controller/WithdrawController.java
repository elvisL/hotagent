package com.huotu.hotagent.agent.controller;

import com.huotu.hotagent.common.constant.ApiResult;
import com.huotu.hotagent.common.constant.ResultCodeEnum;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.service.record.WithdrawRecordService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chendeyu on 2016/2/25.
 */

@Controller
public class WithdrawController {

    private static final Log log = LogFactory.getLog(WithdrawController.class);

    @Autowired
    WithdrawRecordService withdrawRecordService;

    /**
     *代理商提现申请
     */
    @RequestMapping(value = "/applyWithdraw",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult applyWithdraw(@AuthenticationPrincipal Agent agent,double money,String message) throws Exception{
        ApiResult apiResult = null;
        try {
            Boolean bl=withdrawRecordService.withdraw(agent, money ,message);
            if(bl==true){
                apiResult =  ApiResult.resultWith(ResultCodeEnum.SUCCESS);
            }
            else {
                apiResult = ApiResult.resultWith(ResultCodeEnum.WITHDRAW_ERROR);
            }

        }catch (Exception ex){
            log.error(ex.getMessage());
            apiResult = ApiResult.resultWith(ResultCodeEnum.SAVE_DATA_ERROR);
        }
        return apiResult;

    }

}
