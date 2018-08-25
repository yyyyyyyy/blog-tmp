package com.yaochow.serviceuser.service;

public interface RegisterService {

    String register(String accountJson) throws Exception;

    String registerConfirm(String email, String key) throws Exception;
}
