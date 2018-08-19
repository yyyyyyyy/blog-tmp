package com.yaochow.servicecore.common.base;

import com.alibaba.fastjson.JSONObject;
import com.yaochow.servicecore.common.ErrorMsgEnum;
import com.yaochow.servicecore.common.ReturnValueConstant;

public class BaseHystrix {
    protected String doMsg() {
        JSONObject result = new JSONObject();
        result.put(ReturnValueConstant.SUCCESS, false);
        result.put(ReturnValueConstant.ERROR_CODE, ErrorMsgEnum.HYSTRIX_ERROR.getErrorCode());
        result.put(ReturnValueConstant.ERROR_MSG, ErrorMsgEnum.HYSTRIX_ERROR.getErrorMsg());
        return result.toJSONString();
    }
}
