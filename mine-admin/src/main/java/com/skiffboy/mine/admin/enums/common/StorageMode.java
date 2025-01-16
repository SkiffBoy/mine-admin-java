package com.skiffboy.mine.admin.enums.common;

import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;
import io.github.linpeilie.annotations.AutoEnumMapper;
import lombok.Getter;

@Getter
@AutoEnumMapper("code")
public enum StorageMode {
    /**
     * 本地
     */
    local("local", "本地", 1),
    /**
     * 阿里云
     */
    aliyun_oss("oss", "阿里云", 2),
    /**
     * 七牛云
     */
    qiniu_kodo("qiniu", "七牛云", 3),
    /**
     * 腾讯云
     */
    tencent_cos("cos", "腾讯云", 4);

    @EnumValue
    @JsonValue
    private final String code;
    private final String name;
    private final int value;

    StorageMode(String code, String name, int value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }

}
