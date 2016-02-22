package com.huotu.hotagent.admin.controller.withdraw;

import com.huotu.hotagent.common.constant.ApiResult;
import com.huotu.hotagent.service.entity.record.WithdrawRecord;
import com.huotu.hotagent.service.model.WithdrawRequestModel;
import com.huotu.hotagent.service.service.record.WithdrawRecordService;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 提现管理
 * Created by cwb on 2016/2/22.
 */
@Controller
public class WithdrawController {

    @Autowired
    private WithdrawRecordService withdrawRecordService;

    /**
     * 提现记录列表
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "/withdraws",method = RequestMethod.GET)
    public ModelAndView showWithdrawRecords(@RequestParam(required = false,defaultValue = "1")int pageNo) {
        ModelAndView mv = new ModelAndView();
        Page<WithdrawRecord> withdrawRecords = withdrawRecordService.searchRecords(pageNo);
        mv.addObject("records",withdrawRecords.getContent());
        mv.addObject("totalPages",withdrawRecords.getTotalPages());
        mv.addObject("currentPage",pageNo);
        mv.setViewName("withdraw/withdraw_list");
        return mv;
    }

    /**
     * 提现记录详情
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
     * 提现记录修改
     * @param withdrawRecord
     * @return
     */
    @RequestMapping(value = "/withdraws/{id}/edit",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult changeAuditStatus(@ModelAttribute WithdrawRecord withdrawRecord) {
        ApiResult apiResult = new ApiResult();
        return apiResult;
    }

    @ModelAttribute
    public WithdrawRecord editRecord(@PathVariable Long id) {
        return withdrawRecordService.getSpecifiedRecord(id);
    }


}
