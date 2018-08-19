package com.yaochow.servicezuul.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 登陆过滤器：检查用户是否已登陆
 */

public class LoginFilter extends ZuulFilter {

    private Logger log = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 登陆、注销、注册不需要过滤
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String uri = request.getRequestURI();
        if (Objects.equals("/user/login", uri) ||
                Objects.equals("/user/logout", uri) ||
                Objects.equals("/user/register", uri)) {
            return false;
        }
        return true;
    }

    /**
     * 过滤检查session内uid是否有值
     *
     * @return
     */
    @Override
    public Object run() {
        log.info("user filter begin");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        log.info(request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Allow-Credentials", "true"); //是否支持cookie跨域
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        String sessionId = request.getSession().getId();
        log.info("sessionId : {}", sessionId);
        String uid = (String) request.getSession().getAttribute("uid");
        log.info("uid : {}", uid);
        if (StringUtils.isBlank(uid)) {
            try {
                JSONObject result = new JSONObject();
                result.put("success", false);
                result.put("errorMsg", "Login first.");
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(401);
                ctx.getResponse().getWriter().write(result.toJSONString());
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        log.info("user filter finish");
        return null;
    }
}
