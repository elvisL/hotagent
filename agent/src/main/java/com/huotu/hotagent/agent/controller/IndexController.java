package com.huotu.hotagent.agent.controller;

import com.huotu.hotagent.agent.service.AdminService;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chendeyu on 2016/2/1.
 */
@Controller
public class IndexController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AgentService agentService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "views/login";
    }

    @RequestMapping("/loginError")
    public String loginError(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("loginError", request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION"));
        request.getSession().removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        return "redirect:/login";
    }


    @RequestMapping(value = {"", "/", "/index","/loginSuccess"})
    public ModelAndView index(@AuthenticationPrincipal Agent agent) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("balance", agentService.findById(agent.getId()).getBalance());
        modelAndView.setViewName("views/index");
        return  modelAndView;
    }
}
