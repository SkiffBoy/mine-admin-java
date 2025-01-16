package com.skiffboy.mine.admin.exception;

import com.skiffboy.mine.admin.model.ResultCode;
import com.skiffboy.mine.admin.model.response.Result;
import com.skiffboy.mine.util.LocaleMessageUtil;

public class BusinessException extends RuntimeException {
    private Result response;

    public BusinessException(ResultCode code, String messageKey) {
        super(LocaleMessageUtil.getMessage(messageKey));
        if (code == null) {
            code = ResultCode.FAIL;
        }

        this.response = new Result(code, messageKey, null);
    }

    public BusinessException(String messageKey) {
        this(messageKey, null);
    }

    public BusinessException(String messageKey, Throwable cause) {
        super(LocaleMessageUtil.getMessage(messageKey), cause);
        this.response = Result.ofException(messageKey, cause);
    }

    public BusinessException(ResultCode code) {
        this(code, null, null);
    }

    public BusinessException(ResultCode code, String messageKey, Object data) {
        super(LocaleMessageUtil.getMessage(messageKey == null ?
                (code == null ? ResultCode.FAIL.getMessage() : code.getMessage())
                : messageKey));
        if (code == null) {
            code = ResultCode.FAIL;
        }
        if (messageKey == null) {
            messageKey = code.getMessage();
        }
        this.response = new Result(code, messageKey, data);
    }

    public Result getResponse() {
        return response;
    }
}
