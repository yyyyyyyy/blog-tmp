package com.yaochow.servicecore.common.base;

import com.alibaba.fastjson.JSONObject;
import com.yaochow.servicecore.common.ErrorMsgEnum;
import com.yaochow.servicecore.common.ReturnValueConstant;

public class BaseController {

    protected String doError() {
        JSONObject error = new JSONObject();
        error.put(ReturnValueConstant.SUCCESS, false);
        error.put(ReturnValueConstant.ERROR_CODE, ErrorMsgEnum.SYSTEM_ERROR.getErrorCode());
        error.put(ReturnValueConstant.ERROR_MSG, ErrorMsgEnum.SYSTEM_ERROR.getErrorMsg());
        return error.toJSONString();
    }
}
