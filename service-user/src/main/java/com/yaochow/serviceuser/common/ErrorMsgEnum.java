package com.yaochow.serviceuser.common;

public enum ErrorMsgEnum {
    SYSTEM_ERROR("999", "System error."),
    HYSTRIX_ERROR("888", "Hystrix take effect."),
    LOGIN_ERROR("777", "Login failed, cause password incorrect."),
    REGISTER_ERROR("666", "User exist."),
    ACCOUNT_ERROR("555", "account is not exist");

    private String errorCode;
    private String errorMsg;

    ErrorMsgEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
