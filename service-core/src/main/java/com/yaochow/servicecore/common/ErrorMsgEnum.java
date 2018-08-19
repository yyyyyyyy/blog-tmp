package com.yaochow.servicecore.common;

public enum ErrorMsgEnum {
    SYSTEM_ERROR("999", "System error."),
    HYSTRIX_ERROR("888", "Hystrix take effect.");

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
