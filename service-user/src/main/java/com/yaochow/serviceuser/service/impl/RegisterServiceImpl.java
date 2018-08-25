package com.yaochow.serviceuser.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yaochow.serviceuser.common.ErrorMsgEnum;
import com.yaochow.serviceuser.common.ReturnValueConstant;
import com.yaochow.serviceuser.service.AccountService;
import com.yaochow.serviceuser.service.RegisterService;
import com.yaochow.serviceuser.service.UserService;
import com.yaochow.serviceuser.util.MD5;
import com.yaochow.serviceuser.util.MailUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
        String accountRes = accountService.getAccountByEmail(accountReq.getString("email"));
        JSONObject accountResult = JSONObject.parseObject(accountRes);
        if (accountResult.getBoolean(ReturnValueConstant.SUCCESS) &&
                StringUtils.isBlank(accountResult.getString(ReturnValueConstant.RESPONSE))) {
            JSONObject accountReqJson = JSONObject.parseObject(accountJson);
            accountReqJson.put("password", MD5.md5(accountReqJson.getString("password"), "yaochow"));
            accountReqJson.put("confirm", 0);
            String accountInsertRes = accountService.insert(accountReqJson.toJSONString());
            JSONObject accountInsertJson = JSONObject.parseObject(accountInsertRes);
            if (accountInsertJson.getBoolean(ReturnValueConstant.SUCCESS)) {
                JSONObject account = accountInsertJson.getJSONObject(ReturnValueConstant.RESPONSE);
                JSONObject userReq = new JSONObject();
                userReq.put("accountId", account.getString("id"));
                userService.insert(userReq.toJSONString());

                String mailContent = String.format("请点击下方连接确认注册，若非本人操作请忽略此邮件。\r\n" +
                                " http://localhost:8762/user/confirm?email=%s&key=%s",
                        accountReq.getString("email"),
                        MD5.md5(account.getString("id"), "register"));
                MailUtil.send(accountReq.getString("email"), mailContent);
                result.put(ReturnValueConstant.SUCCESS, true);
            } else {
                result.put(ReturnValueConstant.SUCCESS, false);
                result.put(ReturnValueConstant.ERROR_CODE, accountInsertJson.getString(ReturnValueConstant.ERROR_CODE));
                result.put(ReturnValueConstant.ERROR_MSG, accountInsertJson.getString(ReturnValueConstant.ERROR_MSG));
            }
        } else {
            result.put(ReturnValueConstant.SUCCESS, false);
            result.put(ReturnValueConstant.ERROR_CODE, ErrorMsgEnum.REGISTER_EXIST_ERROR.getErrorCode());
            result.put(ReturnValueConstant.ERROR_MSG, ErrorMsgEnum.REGISTER_EXIST_ERROR.getErrorMsg());
        }
        return result.toJSONString();
    }

    @Override
    public String registerConfirm(String email, String key) throws Exception {
        JSONObject result = new JSONObject();
        String accountRes = accountService.getAccountByEmail(email);
        log.info("account : {}", accountRes);
        JSONObject accountResult = JSONObject.parseObject(accountRes);
        if (accountResult.getBoolean(ReturnValueConstant.SUCCESS) &&
                !StringUtils.isBlank(accountResult.getString(ReturnValueConstant.RESPONSE))) {
            JSONObject account = accountResult.getJSONObject(ReturnValueConstant.RESPONSE);
            if (Objects.equals(key, MD5.md5(account.getString("id"), "register"))) {
                JSONObject update = new JSONObject();
                update.put("confirm", "1");
                update.put("id", accountResult.getString("id"));
                accountService.updateAccountById(update.toJSONString());
                result.put(ReturnValueConstant.SUCCESS, true);
            } else {
                result.put(ReturnValueConstant.SUCCESS, false);
                result.put(ReturnValueConstant.ERROR_CODE, ErrorMsgEnum.REGISTER_FAILED_ERROR.getErrorCode());
                result.put(ReturnValueConstant.ERROR_MSG, ErrorMsgEnum.REGISTER_FAILED_ERROR.getErrorMsg());
            }
        }
        return result.toJSONString();
    }
}
