package com.yaochow.serviceuser.service;

import com.yaochow.serviceuser.service.impl.UserServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "service-data", fallback = UserServiceHystrix.class)
public interface UserService {

    @RequestMapping(value = "/user/insert", method = RequestMethod.POST)
    String insert(@RequestBody String userJson);

    @RequestMapping(value = "/user/getUserByAccountId/{accountId}", method = RequestMethod.GET)
    String getUserByAccountId(@PathVariable("accountId") String accountId);

    @RequestMapping(value = "/user/updateByAccountId", method = RequestMethod.POST)
    String updateByAccountId(@RequestBody String userJson);

}
