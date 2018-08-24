package com.yaochow.servicezuul.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@Component
public class FirstFilter extends ZuulFilter {

    private Logger log = LoggerFactory.getLogger(FirstFilter.class);
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String uri = request.getRequestURI();
        if (Objects.equals("/core/note/listNote", uri)) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() {
        log.info("first filter begin");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String uid = (String) request.getSession().getAttribute("uid");
        if (StringUtils.isBlank(uid)) {
            try {
                JSONObject result = new JSONObject();
                result.put("success", false);
                result.put("errorMsg", "Login first.");
                result.put("errorCode", "1");
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(200);
                ctx.getResponse().getWriter().write(result.toJSONString());
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        log.info("first filter finish");
        return null;
    }
}
