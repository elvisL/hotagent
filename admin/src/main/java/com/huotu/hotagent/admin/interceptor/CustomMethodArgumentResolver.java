package com.huotu.hotagent.admin.interceptor;

import com.huotu.hotagent.service.common.AgentType;
import com.huotu.hotagent.service.common.AuditStatus;
import com.huotu.hotagent.service.entity.role.agent.AgentLevel;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
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

        }
        String statusValue = request.getParameter("auditStatus");
        if("0".equals(statusValue)) {
            return AuditStatus.APPLYING;
        }
        if("1".equals(statusValue)) {
            return AuditStatus.PROCESSED;
        }
        if("2".equals(statusValue)) {
            return AuditStatus.FAIL;
        }
        return null;
    }

    private boolean isAgentLevel(MethodParameter parameter) {
        return parameter.getParameterType() == AgentLevel.class;
    }
}
