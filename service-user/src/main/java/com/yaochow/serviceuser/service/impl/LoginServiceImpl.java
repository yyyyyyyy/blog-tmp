package com.yaochow.serviceuser.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaochow.serviceuser.common.ErrorMsgEnum;
import com.yaochow.serviceuser.common.ReturnValueConstant;
import com.yaochow.serviceuser.service.AccountService;
import com.yaochow.serviceuser.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    private Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Autowired
    private AccountService accountService;
    @Autowired
    private HttpServletRequest request;

    @Override
    public String login(String accountJson) {
        JSONObject accountReq = JSONObject.parseObject(accountJson);
        String account = accountService.getAccountByUsername(accountReq.getString("username"));
        JSONObject accountRes = JSONObject.parseObject(account);
        JSONObject accountResponseJson = accountRes.getJSONObject(ReturnValueConstant.RESPONSE);

        if (!accountRes.getBoolean(ReturnValueConstant.SUCCESS)) {
            JSONObject result = new JSONObject();
            result.put(ReturnValueConstant.SUCCESS, false);
            result.put(ReturnValueConstant.ERROR_CODE, accountRes.getString(ReturnValueConstant.ERROR_CODE));
            result.put(ReturnValueConstant.ERROR_MSG, accountRes.getString(ReturnValueConstant.ERROR_MSG));
            return result.toJSONString();
        }

        if (accountResponseJson == null) {

            JSONObject result = new JSONObject();
            result.put(ReturnValueConstant.SUCCESS, false);
            result.put(ReturnValueConstant.ERROR_CODE, ErrorMsgEnum.ACCOUNT_ERROR.getErrorCode());
            result.put(ReturnValueConstant.ERROR_MSG, ErrorMsgEnum.ACCOUNT_ERROR.getErrorMsg());
            return result.toJSONString();
        }

        if (Objects.equals(accountReq.getString("password"), accountResponseJson.getString("password"))) {
            log.info("{}, {}", request.getSession().getId(), accountResponseJson.getString("id"));
//            request.getSession().setAttribute("uid", accountResponseJson.getString("id"));
            JSONObject result = new JSONObject();
            result.put(ReturnValueConstant.SUCCESS, true);
            result.put("uid", accountResponseJson.getString("id"));
            return result.toJSONString();
        } else {
            JSONObject result = new JSONObject();
            result.put(ReturnValueConstant.SUCCESS, false);
            result.put(ReturnValueConstant.ERROR_CODE, ErrorMsgEnum.LOGIN_ERROR.getErrorCode());
            result.put(ReturnValueConstant.ERROR_MSG, ErrorMsgEnum.LOGIN_ERROR.getErrorMsg());
            return result.toJSONString();
        }
    }
}
