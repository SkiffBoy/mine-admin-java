package com.skiffboy.mine.admin.enums.user;

import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;
import com.skiffboy.mine.util.LocaleMessageUtil;
import io.github.linpeilie.annotations.AutoEnumMapper;
import lombok.Getter;

@Getter
@AutoEnumMapper("code")
public enum UserType {
    SYSTEM(100, "user.enums.type.100"),
    USER(200, "user.enums.type.200");

    @EnumValue
    @JsonValue
    private final int code;
    private final String message;

    UserType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return LocaleMessageUtil.getMessage(message);
    }

}
