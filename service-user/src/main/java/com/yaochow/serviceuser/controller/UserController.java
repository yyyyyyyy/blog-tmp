package com.yaochow.serviceuser.controller;

import com.yaochow.serviceuser.common.base.BaseController;
import com.yaochow.serviceuser.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 获取、修改用户信息
 */
@RestController
public class UserController extends BaseController {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUserByAccountId/{accountId}", method = RequestMethod.GET)
    public String getUserByAccountId(@PathVariable String accountId) {
        long start = System.currentTimeMillis();
        String result;
        log.info("get user by accountId begin");
        try {
            result = userService.getUserByAccountId(accountId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = doError();
        }
        log.info("get user by accountId, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }

    @RequestMapping(value = "/updateUserByAccountId", method = RequestMethod.POST)
    public String updateUserByAccountId(@RequestBody String userJson) {
        long start = System.currentTimeMillis();
        String result;
        log.info("update user by accountId, param : {}", userJson);
        try {
            result = userService.updateByAccountId(userJson);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = doError();
        }
        log.info("update user by accountId, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }
}
