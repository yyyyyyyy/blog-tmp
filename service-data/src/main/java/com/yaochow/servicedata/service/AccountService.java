package com.yaochow.servicedata.service;

import com.yaochow.servicedata.entity.Account;

public interface AccountService {

    Account insert(Account account);

    Account getAccountByUsername(String username);

    Account updateAccountById(Account account);

    Account getAccountByEmail(String email);
}
