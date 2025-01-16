package com.skiffboy.mine.admin.enums.menu;

import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;
import com.skiffboy.mine.util.LocaleMessageUtil;
import io.github.linpeilie.annotations.AutoEnumMapper;
import lombok.Getter;

@Getter
@AutoEnumMapper("code")
public enum Type {
    /**
     * 菜单
     */
    Menu("M", "menu.enums.type.M"),
    /**
     * 按钮
     */
    Button("B", "menu.enums.type.B"),
    /**
     * 外链
     */
    Link("L", "menu.enums.type.L"),
    /**
     * iFrame
     */
    IFrame("I", "menu.enums.type.I");

    @EnumValue
    @JsonValue
    private final String code;
    private final String message;

    Type(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return LocaleMessageUtil.getMessage(message);
    }

}
