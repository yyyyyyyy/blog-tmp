package com.yaochow.servicedata.service.impl;

import com.yaochow.servicedata.common.base.BaseService;
import com.yaochow.servicedata.entity.Account;
import com.yaochow.servicedata.repository.AccountRepository;
import com.yaochow.servicedata.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl extends BaseService implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account insert(Account account) {
        setInsertParam(account);
        return accountRepository.insert(account);
    }

    @Override
    public Account getAccountByUsername(String username) {
        Account account = new Account();
        account.setUsername(username);
        setUnDeleted(account);
        Optional<Account> optional = accountRepository.findOne(Example.of(account));
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public Account updateAccountById(Account account) {

        Account accountQuery = new Account();
        accountQuery.setId(account.getId());
        Optional<Account> optional = accountRepository.findOne(Example.of(accountQuery));
        if (account.getConfirm() != null) {
            optional.get().setConfirm(account.getConfirm());
        }
        if (account.getPassword() != null) {
            optional.get().setPassword(account.getPassword());
        }
        setUpdateParam(optional.get());
        return accountRepository.save(optional.get());
    }

    @Override
    public Account getAccountByEmail(String email) {
        Account account = new Account();
        account.setEmail(email);
        setUnDeleted(account);
        Optional<Account> optional = accountRepository.findOne(Example.of(account));
        return optional.isPresent() ? optional.get() : null;
    }
}
