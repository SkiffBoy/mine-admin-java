package com.skiffboy.mine.admin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(enumAsRef = true, title = "ResultCode", description = "响应码", type = "integer", defaultValue = "200")
@Getter
public enum ResultCode {
    SUCCESS(200, "result.success"),
    FAIL(500, "result.fail"),
    UNAUTHORIZED(401, "result.unauthorized"),
    FORBIDDEN(403, "result.forbidden"),
    NOT_FOUND(404, "result.not_found"),
    METHOD_NOT_ALLOWED(405, "result.method_not_allowed"),
    NOT_ACCEPTABLE(406, "result.not_acceptable"),
    UNPROCESSABLE_ENTITY(422, "result.conflict");
    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
