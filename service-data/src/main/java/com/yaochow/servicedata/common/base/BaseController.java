package com.yaochow.servicedata.common.base;

import com.alibaba.fastjson.JSONObject;
import com.yaochow.servicedata.common.ErrorMsgEnum;
import com.yaochow.servicedata.common.ReturnValueConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BaseController {

    protected String doSuccess(Object object) {
        JSONObject success = new JSONObject();
        success.put(ReturnValueConstant.SUCCESS, true);
        if (object != null) {
            if (object instanceof List) {
                if (((List) object).size() == 0) {
                    return success.toJSONString();
                }
            }
            success.put(ReturnValueConstant.RESPONSE, JSONObject.toJSONString(object));
        }
        return success.toJSONString();
    }

    protected String doError() {
        JSONObject error = new JSONObject();
        error.put(ReturnValueConstant.SUCCESS, false);
        error.put(ReturnValueConstant.ERROR_CODE, ErrorMsgEnum.SYSTEM_ERROR.getErrorCode());
        error.put(ReturnValueConstant.ERROR_MSG, ErrorMsgEnum.SYSTEM_ERROR.getErrorMsg());
        return error.toJSONString();
    }

    protected boolean checkSessionLost(HttpServletRequest request) {
        if (request.getSession().getAttribute("uid") != null) {
            return false;
        }
        return true;
    }

    protected String doSessionError() {
        JSONObject error = new JSONObject();
        error.put(ReturnValueConstant.SUCCESS, false);
        error.put(ReturnValueConstant.ERROR_CODE, ErrorMsgEnum.SESSION_ERROR.getErrorCode());
        error.put(ReturnValueConstant.ERROR_MSG, ErrorMsgEnum.SESSION_ERROR.getErrorMsg());
        return error.toJSONString();
    }
}
