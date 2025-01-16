package com.skiffboy.mine.admin.enums.common;

import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;
import com.skiffboy.mine.util.LocaleMessageUtil;
import io.github.linpeilie.annotations.AutoEnumMapper;
import lombok.Getter;

@Getter
@AutoEnumMapper("code")
public enum Status {
    Normal(1, "user.enums.status.1"),
    Disable(2, "user.enums.status.2");

    @EnumValue
    @JsonValue
    public final int code;
    private final String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return LocaleMessageUtil.getMessage(message);
    }

}
