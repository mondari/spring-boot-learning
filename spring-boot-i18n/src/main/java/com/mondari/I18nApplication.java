package com.mondari;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@RestController
@SpringBootApplication
public class I18nApplication {

    public static final String KEY_GREETINGS = "greetings";
    public static final String KEY_INQUIRY = "inquiry";
    public static final String KEY_FAREWELL = "farewell";
    public static final String KEY_MESSAGE_SOURCE = "MessageSource";
    public static final String KEY_RESOURCE_BUNDLE = "ResourceBundle";

    @Autowired
    LocaleResolver localeResolver;

    @Autowired
    MessageSource messageSource;

    public static void main(String[] args) {
        SpringApplication.run(I18nApplication.class, args);
    }

    @GetMapping
    public Map<String, Object> get(HttpServletRequest request) {
        Locale locale = localeResolver.resolveLocale(request);

        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put(KEY_GREETINGS, messageSource.getMessage(KEY_GREETINGS, new Object[]{"boys", "girls"}, locale));
        sourceMap.put(KEY_INQUIRY, messageSource.getMessage(KEY_INQUIRY, null, locale));
        sourceMap.put(KEY_FAREWELL, messageSource.getMessage(KEY_FAREWELL, null, locale));

        // Spring 的 MessageSource 其实是对 JDK 中的 ResourceBundle 进行了封装
        // 以下代码参考JDK官方教程：https://docs.oracle.com/javase/tutorial/i18n/intro/steps.html
        Map<String, Object> bundleMap = new HashMap<>();
        ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", locale);
        bundleMap.put(KEY_GREETINGS, bundle.getString(KEY_GREETINGS));
        bundleMap.put(KEY_INQUIRY, bundle.getString(KEY_INQUIRY));
        bundleMap.put(KEY_FAREWELL, bundle.getString(KEY_FAREWELL));

        Map<String, Object> i18nMap = new HashMap<>();
        i18nMap.put(KEY_MESSAGE_SOURCE, sourceMap);
        i18nMap.put(KEY_RESOURCE_BUNDLE, bundleMap);
        return i18nMap;
    }

}
