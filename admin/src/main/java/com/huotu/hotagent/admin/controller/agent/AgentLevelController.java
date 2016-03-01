package com.huotu.hotagent.admin.controller.agent;

import com.huotu.hotagent.common.constant.SysConstant;
import com.huotu.hotagent.common.utils.CommonUtils;
import com.huotu.hotagent.service.entity.role.agent.AgentLevel;
import com.huotu.hotagent.service.service.role.agent.AgentLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * 代理商级别
 * Created by cwb on 2016/2/25.
 */
@Controller
public class AgentLevelController {

    @Autowired
    private AgentLevelService agentLevelService;

    @RequestMapping(value = "/agentLevels",method = RequestMethod.GET)
    public ModelAndView showLevels(@RequestParam(required = false,defaultValue = "1") int pageNo) {
        ModelAndView mav = new ModelAndView();
        Page<AgentLevel> levels = agentLevelService.findAgentLevels(pageNo);
        int totalPages = levels.getTotalPages();
        mav.addObject("currentPage",pageNo);
        mav.addObject("totalPages",totalPages);
        mav.addObject("totalRecords",levels.getTotalElements());
        mav.addObject("levelList",levels.getContent());
        mav.addObject("hasNext",levels.hasNext());
        mav.addObject("hasPrevious",levels.hasPrevious());
        mav.setViewName("agent/agentLevelList");

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

}
