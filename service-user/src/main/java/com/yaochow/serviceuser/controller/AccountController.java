package com.yaochow.serviceuser.controller;

import com.yaochow.serviceuser.common.base.BaseController;
import com.yaochow.serviceuser.service.AccountService;
import com.yaochow.serviceuser.service.LoginService;
import com.yaochow.serviceuser.service.RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 登陆、注册、修改登陆密码
 */
@RestController
public class AccountController extends BaseController {
    private Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private RegisterService registerServiceImpl;
    @Autowired
    private LoginService loginServiceImpl;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody String accountJson) {

        long start = System.currentTimeMillis();
        String result;
        log.info("login, param : {}", accountJson);
        try {
            result = loginServiceImpl.login(accountJson);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = doError();
        }
        log.info("login, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody String accountJson) {
        long start = System.currentTimeMillis();
        String result;
        log.info("register, param : {}", accountJson);
        try {
            result = registerServiceImpl.register(accountJson);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = doError();
        }
        log.info("register, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }

    @RequestMapping(value = "/updateAccountById", method = RequestMethod.POST)
    public String updateAccountById(@RequestBody String accountJson) {
        long start = System.currentTimeMillis();
        String result;
        log.info("update account by id, param : {}", accountJson);
        try {
            result = accountService.updateAccountById(accountJson);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = doError();
        }
        log.info("update account by id, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }
}
