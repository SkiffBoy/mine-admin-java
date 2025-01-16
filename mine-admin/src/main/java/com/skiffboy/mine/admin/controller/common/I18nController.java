package com.skiffboy.mine.admin.controller.common;

import com.skiffboy.mine.util.LocaleMessageUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class I18nController {

    @GetMapping("/i18n/{messageKey}")
    public String i18n(@PathVariable String messageKey) {
        return LocaleMessageUtil.getMessage(messageKey);
    }

}
