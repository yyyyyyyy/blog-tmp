package com.yaochow.serviceuser.service.impl;

import com.yaochow.serviceuser.common.base.BaseHystrix;
import com.yaochow.serviceuser.service.AccountService;
import org.springframework.stereotype.Component;

@Component
public class AccountServiceHystrix extends BaseHystrix implements AccountService {
    @Override
    public String insert(String accountJson) {
        return doMsg();
    }

    @Override
    public String getAccountByUsername(String username) {
        return doMsg();
    }

    @Override
    public String updateAccountById(String accountJson) {
        return doMsg();
    }
}
