package com.yaochow.servicedata.service;

import com.yaochow.servicedata.entity.User;

public interface UserService {

    User insert(User user);

    User updateByAccountId(User user);

    User getUserByAccountId(String accountId);
}
