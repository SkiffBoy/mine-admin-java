package com.skiffboy.mine.admin.model.response;

import com.skiffboy.mine.admin.model.ResultCode;
import com.skiffboy.mine.util.LocaleMessageUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "Api Response", description = "登录成功")
public class Result<T> {

    @Schema(description = "响应码")
    private int code;

    @Schema(title = "响应消息", type = "string")
    private String message;

    @Schema(title = "响应数据")
    private T data;

    public Result(ResultCode code) {
        this.code = code.getCode();
        this.message = LocaleMessageUtil.getMessage(code.getMessage());
    }

    public Result(ResultCode code, T data) {
        this.code = code.getCode();
        this.data = data;
        this.message = LocaleMessageUtil.getMessage(code.getMessage());
    }

    public Result(ResultCode code, String messageKey, T data) {
        this.code = code.getCode();
        String message = LocaleMessageUtil.getMessage(messageKey);
        this.message = message == null ? messageKey : message;
        this.data = data;
    }

    public static <T> Result<T> ofException(String messageKey, Throwable e) {
        Result<T> result = new Result<>(ResultCode.UNPROCESSABLE_ENTITY);
        String message = LocaleMessageUtil.getMessage(messageKey);
        String exceptionMessage = (e == null || e.getCause() == null) ? "" : ": " + e.getCause().getMessage();
        result.setMessage(message + exceptionMessage);
        return result;
    }

}
