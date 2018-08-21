package com.yaochow.servicedata.controller;

import com.alibaba.fastjson.JSONObject;
import com.yaochow.servicedata.common.base.BaseController;
import com.yaochow.servicedata.entity.User;
import com.yaochow.servicedata.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private UserService userServiceImpl;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(@RequestBody String userJson) {
        long start = System.currentTimeMillis();
        String result;
        log.info("insert, param : {}", userJson);
        try {
            User userReq = JSONObject.parseObject(userJson, User.class);
            if (checkSessionLost(request)) {
                log.info("session lost");
                result = doSessionError();
                return result;
            }
            String accountId = (String) request.getSession().getAttribute("uid");
            userReq.setAccountId(accountId);
            User userRes = userServiceImpl.insert(userReq);
            result = doSuccess(userRes);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            result = doError();
        }
        log.info("insert, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }

    @RequestMapping(value = "/getUserByAccountId", method = RequestMethod.GET)
    public String getUserByAccountId() {
        long start = System.currentTimeMillis();
        String result;
        try {
            log.info("{}, {}", request.getSession().getId(), request.getSession().getAttribute("uid"));
            if (checkSessionLost(request)) {
                log.info("session lost");
                result = doSessionError();
                return result;
            }
            String accountId = (String) request.getSession().getAttribute("uid");
            log.info("get user by accountId : {}", accountId);
            User userRes = userServiceImpl.getUserByAccountId(accountId);
            result = doSuccess(userRes);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
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
            User userReq = JSONObject.parseObject(userJson, User.class);
            if (checkSessionLost(request)) {
                log.info("session lost");
                result = doSessionError();
                return result;
            }
            String accountId = (String) request.getSession().getAttribute("uid");
            userReq.setAccountId(accountId);
            User userRes = userServiceImpl.updateByAccountId(userReq);
            result = doSuccess(userRes);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            result = doError();
        }
        log.info("update user by accountId, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }
}
