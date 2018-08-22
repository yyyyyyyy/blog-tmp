package com.yaochow.serviceuser.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaochow.serviceuser.common.ErrorMsgEnum;
import com.yaochow.serviceuser.common.ReturnValueConstant;
import com.yaochow.serviceuser.service.AccountService;
import com.yaochow.serviceuser.service.RegisterService;
import com.yaochow.serviceuser.service.UserService;
import com.yaochow.serviceuser.util.MD5;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    private Logger log = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @Override
    public String register(String accountJson) throws Exception {
        JSONObject result = new JSONObject();
        JSONObject accountReq = JSONObject.parseObject(accountJson);
        String accountRes = accountService.getAccountByUsername(accountReq.getString("username"));
        log.info("account : {}", accountRes);
        JSONObject accountResult = JSONObject.parseObject(accountRes);
        if (accountResult.getBoolean(ReturnValueConstant.SUCCESS) &&
                StringUtils.isBlank(accountResult.getString(ReturnValueConstant.RESPONSE))) {
            JSONObject accountReqJson = JSONObject.parseObject(accountJson);
            accountReqJson.put("password", MD5.md5(accountReqJson.getString("password"),"yaochow"));
            String accountInsertRes = accountService.insert(accountReqJson.toJSONString());
            JSONObject accountInsertJson = JSONObject.parseObject(accountInsertRes);
            if (accountInsertJson.getBoolean(ReturnValueConstant.SUCCESS)) {
                JSONObject account = accountInsertJson.getJSONObject(ReturnValueConstant.RESPONSE);
                JSONObject userReq = new JSONObject();
                userReq.put("accountId", account.getString("id"));
                userService.insert(userReq.toJSONString());
                result.put(ReturnValueConstant.SUCCESS, true);
            } else {
                result.put(ReturnValueConstant.SUCCESS, false);
                result.put(ReturnValueConstant.ERROR_CODE, accountInsertJson.getString(ReturnValueConstant.ERROR_CODE));
                result.put(ReturnValueConstant.ERROR_MSG, accountInsertJson.getString(ReturnValueConstant.ERROR_MSG));
            }

        } else {
            result.put(ReturnValueConstant.SUCCESS, false);
            result.put(ReturnValueConstant.ERROR_CODE, ErrorMsgEnum.REGISTER_ERROR.getErrorCode());
            result.put(ReturnValueConstant.ERROR_MSG, ErrorMsgEnum.REGISTER_ERROR.getErrorMsg());
        }
        return result.toJSONString();
    }
}
