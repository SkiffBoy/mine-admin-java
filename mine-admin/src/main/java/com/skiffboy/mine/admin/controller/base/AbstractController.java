package com.skiffboy.mine.admin.controller.base;

import com.skiffboy.mine.admin.model.ResultCode;
import com.skiffboy.mine.admin.model.holder.CurrentUser;
import com.skiffboy.mine.admin.model.response.Result;

public class AbstractController {

    protected Result success() {
        return new Result(ResultCode.SUCCESS);
    }

    protected Result success(Object data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    protected Result success(Object data, String message) {
        return new Result(ResultCode.SUCCESS, message, data);
    }

    protected Result error(Object data) {
        return new Result(ResultCode.FAIL, data);
    }

    protected Result error(String message, Object data) {
        return new Result(ResultCode.FAIL, message, data);
    }

    protected Result json(ResultCode code, Object data) {
        return new Result(code, data);
    }

    protected Result json(ResultCode code, Object data, String message) {
        return new Result(code, message, data);
    }

    protected long getCurrentUserId() {
        return CurrentUser.getId();
    }

}
