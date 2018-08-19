package com.yaochow.servicedata.controller;

import com.alibaba.fastjson.JSONObject;
import com.yaochow.servicedata.common.base.BaseController;
import com.yaochow.servicedata.entity.Account;
import com.yaochow.servicedata.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController extends BaseController {

    @Autowired
    private AccountService accountServiceImpl;
    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(@RequestBody String accountJson) {
        long start = System.currentTimeMillis();
        String result;
        log.info("insert, param : {}", accountJson);
        try {
            Account accountReq = JSONObject.parseObject(accountJson, Account.class);
            Account accountRes = accountServiceImpl.insert(accountReq);
            result = doSuccess(accountRes);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            result = doError();
        }
        log.info("insert, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }

    @RequestMapping(value = "/getAccountByUsername/{username}", method = RequestMethod.GET)
    public String getAccountByUsername(@PathVariable String username) {
        long start = System.currentTimeMillis();
        String result;
        log.info("get account by username : {}", username);
        try {
            Account accountRes = accountServiceImpl.getAccountByUsername(username);
            result = doSuccess(accountRes);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            result = doError();
        }
        log.info("get account by username, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }

    @RequestMapping(value = "/updateAccountById", method = RequestMethod.POST)
    public String updateAccountById(@RequestBody String accountJson) {
        long start = System.currentTimeMillis();
        String result;
        log.info("update account by id, param : {}", accountJson);
        try {
            Account accountReq = JSONObject.parseObject(accountJson, Account.class);
//            if (checkSessionLost(request)){
//                log.info("session lost");
//                result = doSessionError();
//                return result;
//            }
//            String accountId = (String) request.getSession().getAttribute("uid");
//            accountReq.setId(accountId);
            Account accountRes = accountServiceImpl.updateAccountById(accountReq);
            result = doSuccess(accountRes);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            result = doError();
        }
        log.info("update account by id, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }

}
