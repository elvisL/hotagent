package com.huotu.hotagent.admin.interceptor;

import com.huotu.hotagent.service.common.AuditStatus;
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
        return parameter.getParameterType() == AuditStatus.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String statusValue = request.getParameter("auditStatus");
        if("0".equals(statusValue)) {
            return AuditStatus.APPLYING;
        }
        if("1".equals(statusValue)) {
            return AuditStatus.PROCESSED;
        }
        return null;
    }
}
