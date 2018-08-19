package com.yaochow.serviceuser.service.impl;

import com.yaochow.serviceuser.service.AccountService;
import com.yaochow.serviceuser.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    private AccountService accountService;

    @Override
    public String changePassword(String accountJson) {
        return accountService.updateAccountById(accountJson);
    }
}
