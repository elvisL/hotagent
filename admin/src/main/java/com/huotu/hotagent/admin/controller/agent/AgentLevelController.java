package com.huotu.hotagent.admin.controller.agent;

import com.huotu.hotagent.service.entity.role.agent.AgentLevel;
import com.huotu.hotagent.service.service.role.agent.AgentLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
        mav.addObject("currentPage",pageNo);
        mav.addObject("totalPages",levels.getTotalPages());
        mav.addObject("totalRecords",levels.getTotalElements());
        mav.addObject("levelList",levels.getContent());
        mav.addObject("hasNext",levels.hasNext());
        mav.addObject("hasPrevious",levels.hasPrevious());
        mav.setViewName("agent/agentLevelList");
        return mav;
    }
}
