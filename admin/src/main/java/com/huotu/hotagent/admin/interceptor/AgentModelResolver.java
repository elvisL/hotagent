package com.huotu.hotagent.admin.interceptor;

import com.huotu.hotagent.service.common.AgentType;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.agent.AgentLevel;
import com.huotu.hotagent.service.service.role.agent.AgentLevelService;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * agent model
 * Created by cwb on 2016/2/17.
 */
@Component
public class AgentModelResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentLevelService agentLevelService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == Agent.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        return initAgentModel(request);
    }

    private Agent initAgentModel(HttpServletRequest request) throws Exception{
        String agentId = request.getParameter("id");
        Agent agent;
        if(StringUtils.isEmpty(agentId)) {
            agent = new Agent();
            agent.setCreateTime(new Date());
        }else {
            agent = agentService.findById(Long.parseLong(agentId));
            if(agent == null) {
                throw new Exception("¥˙¿Ì…Ã±‡∫≈¥ÌŒÛ!");
            }
        }
        Map<String,String[]> params = request.getParameterMap();
        for (Map.Entry<String,String[]> entry : params.entrySet()){
            String[] paramValues = entry.getValue();
            try {
                switch (entry.getKey()) {
                    case "username":
                        agent.getClass().getMethod("setUsername",String.class).invoke(agent, paramValues[0]);
                        break;
                    case "password":
                        agent.getClass().getMethod("setPassword",String.class).invoke(agent, paramValues[0]);
                        break;
                    default:
                        Field field = agent.getClass().getDeclaredField(entry.getKey());
                        field.setAccessible(true);
                        Class<?> classType = field.getType();
                        if(classType == String.class) {
                            field.set(agent,paramValues[0]);
                        }else if(classType == Boolean.class) {
                            field.set(agent,Boolean.valueOf(paramValues[0]));
                        }else if(classType == AgentLevel.class) {
                            AgentLevel agentLevel = agentLevelService.findByLevel(Integer.parseInt(paramValues[0]));
                            field.set(agent,agentLevel);
                        }else if(classType == AgentType.class) {
                            field.set(agent,AgentType.getAgentType(Integer.parseInt(paramValues[0])));
                        }
                }
            }catch (NoSuchFieldException e) {
                continue;
            }

        }
        return agent;
    }

    private Agent editAgent(HttpServletRequest request) {
        return null;
    }

    private Agent newAgent(HttpServletRequest request) {
        return null;
    }
}
