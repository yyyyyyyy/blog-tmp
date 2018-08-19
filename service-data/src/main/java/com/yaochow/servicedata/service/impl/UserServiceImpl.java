package com.yaochow.servicedata.service.impl;

import com.yaochow.servicedata.common.base.BaseService;
import com.yaochow.servicedata.entity.User;
import com.yaochow.servicedata.repository.UserRepository;
import com.yaochow.servicedata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User insert(User user) {
        setInsertParam(user);
        return userRepository.insert(user);
    }

    @Override
    public User updateByAccountId(User user) {
        setUpdateParam(user);
        return userRepository.save(user);
    }

    @Override
    public User getUserByAccountId(String accountId) {
        User user = new User();
        user.setAccountId(accountId);
        setUnDeleted(user);
        Optional<User> optional = userRepository.findOne(Example.of(user));
        return optional.isPresent() ? optional.get() : null;
    }
}
