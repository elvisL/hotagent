package com.huotu.hotagent.admin.interceptor;

import com.huotu.hotagent.service.common.AgentType;
import com.huotu.hotagent.service.common.AuditStatus;
import com.huotu.hotagent.service.entity.role.agent.AgentLevel;
import com.huotu.hotagent.service.service.role.agent.AgentLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义参数初始化方案
 * Created by cwb on 2016/2/23.
 */
@Component
public class CustomMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private AgentLevelService agentLevelService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> paramType = parameter.getParameterType();
        if(paramType == AgentLevel.class) {
            return true;
        }else if(paramType == AgentType.class) {
            return true;
        }else if(paramType == AuditStatus.class) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if(isAgentLevel(parameter)) {
            String level = request.getParameter("level");
            if(StringUtils.isEmpty(level)) {
                throw new Exception("argument:level can not be null");
            }
            return agentLevelService.findByLevel(Integer.parseInt(level));
        }
        if(isAgentType(parameter)) {
            String type = request.getParameter("type");
            if(StringUtils.isEmpty(type)) {
                throw new Exception("argument:type can not be null");
            }
            return AgentType.getAgentType(Integer.parseInt(type));
        }
        if(isAuditStatus(parameter)) {
            String auditStatus = request.getParameter("auditStatus");
            if(StringUtils.isEmpty(auditStatus)) {
                throw new Exception("argument:auditStatus can not be null");
            }
            return AuditStatus.getAuditStatus(Integer.parseInt(auditStatus));
        }
        return null;
    }

    private boolean isAgentType(MethodParameter parameter) {
        return parameter.getParameterType() == AgentType.class;
    }

    private boolean isAuditStatus(MethodParameter parameter) {
        return parameter.getParameterType() == AuditStatus.class;
    }

    private boolean isAgentLevel(MethodParameter parameter) {
        return parameter.getParameterType() == AgentLevel.class;
    }
}
