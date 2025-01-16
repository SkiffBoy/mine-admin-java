package com.skiffboy.mine.admin.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 本地化语言配置
 */
@Configuration
public class LocaleMessageConfig {
    /**
     * 支持的本地化语言
     */
    public static final Set<Locale> SUPPORT_LOCALES = new LinkedHashSet<>();
    /**
     * 本地化语言文件后缀名(扩展名)
     */
    private static final List<String> FILE_EXTENSIONS = Arrays.asList(".properties", ".yaml", ".yml");
    /**
     * 所有本地化语言
     */
    private static final List<String> LOCALE_NAMES = Arrays.stream(Locale.getAvailableLocales())
            .flatMap(lang -> FILE_EXTENSIONS.stream().map(ext -> "_" + lang + ext))
            .sorted((a, b) -> b.length() - a.length()).toList();
    /**
     * 本地化语言-文件名列表
     */
    private static String[] baseNames;

    public LocaleMessageConfig() {
        File i18nDirectory = new File(getClass().getResource("/i18n").getPath());
        List<String> messageNames = readBaseNamesToLocales(i18nDirectory, SUPPORT_LOCALES);
        LocaleMessageConfig.baseNames = ArrayUtil.toArray(messageNames, String.class);
    }

    /**
     * 读取 i18 目录下的所有本地化语言文件
     *
     * @param i18nDirectory 语言文件目录
     * @param locales       支持的语言
     * @return
     */
    public List<String> readBaseNamesToLocales(File i18nDirectory, Set<Locale> locales) {
        List<String> messageNames = new ArrayList<>();
        String root = i18nDirectory.getName();
        for (File file : i18nDirectory.listFiles()) {
            if (file.isDirectory()) {
                String directory = root + "/" + file.getName();
                List<String> children = readBaseNamesToLocales(file, locales);
                if (CollUtil.isNotEmpty(children)) {
                    for (String messageName : children) {
                        messageNames.add(directory + "/" + messageName);
                    }
                }
            } else {
                FILE_EXTENSIONS.stream().filter(ext -> file.getName().endsWith(ext)).forEach(ext -> {
                    // 只读取以支持的语言名结尾的 properties 文件
                    String localeFileSuffix = LOCALE_NAMES.stream().filter(file.getName()::endsWith).findFirst().orElse(null);
                    if (localeFileSuffix != null) {
                        String fileName = file.getName();
                        String localeName = localeFileSuffix.substring(1, localeFileSuffix.length() - ext.length());
                        String messageName = fileName.substring(0, fileName.length() - localeFileSuffix.length());
                        messageNames.add(messageName);
                        locales.add(LocaleUtils.toLocale(localeName));
                    }
                });
            }
        }
        return messageNames.stream().distinct().sorted().collect(Collectors.toList());
    }

    /**
     * 设置读取的文件及编码，避免乱码问题
     *
     * @return
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(baseNames);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
