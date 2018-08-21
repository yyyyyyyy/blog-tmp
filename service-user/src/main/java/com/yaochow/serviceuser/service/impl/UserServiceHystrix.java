package com.yaochow.serviceuser.service.impl;

import com.yaochow.serviceuser.common.base.BaseHystrix;
import com.yaochow.serviceuser.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceHystrix extends BaseHystrix implements UserService {
    @Override
    public String insert(String accountJson) {
        return doMsg();
    }

    @Override
    public String getUserByAccountId() {
        return doMsg();
    }

    @Override
    public String updateByAccountId(String userJson) {
        return doMsg();
    }
}
