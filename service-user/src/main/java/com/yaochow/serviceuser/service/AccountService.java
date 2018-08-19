package com.yaochow.serviceuser.service;

import com.yaochow.serviceuser.service.impl.AccountServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "service-data", fallback = AccountServiceHystrix.class)
public interface AccountService {

    @RequestMapping(value = "/account/insert", method = RequestMethod.POST)
    String insert(@RequestBody String accountJson);

    @RequestMapping(value = "/account/getAccountByUsername/{username}", method = RequestMethod.GET)
    String getAccountByUsername(@PathVariable("username") String username);

    @RequestMapping(value = "/account/updateAccountById", method = RequestMethod.POST)
    String updateAccountById(@RequestBody String accountJson);
}
