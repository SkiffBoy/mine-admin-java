package com.skiffboy.mine.util;

import cn.hutool.extra.spring.SpringUtil;
import com.skiffboy.mine.admin.config.LocaleMessageConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

public class LocaleMessageUtil {
    public static final MessageSource messageSource;
    private static ThreadLocal<Locale> localeHolder = new ThreadLocal<>();
    private static Locale DEFAULT_LOCALE = Locale.SIMPLIFIED_CHINESE;

    static {
        messageSource = SpringUtil.getBean("messageSource");
    }

    public static Locale getLocale() {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request != null) {
            Locale locale = RequestContextUtils.getLocale(request);
            if (LocaleMessageConfig.SUPPORT_LOCALES.contains(locale)) {
                LocaleMessageUtil.localeHolder.set(locale);
                return locale;
            }
        }
        if (localeHolder.get() != null) {
            return localeHolder.get();
        }
        LocaleMessageUtil.localeHolder.set(DEFAULT_LOCALE);
        return DEFAULT_LOCALE;
    }

    public static String getMessage(String messageKey, Object... objs) {
        try {
            return messageSource.getMessage(messageKey, objs, getLocale());
        } catch (NoSuchMessageException e) {
            return String.valueOf(messageKey);
        }
    }
}
