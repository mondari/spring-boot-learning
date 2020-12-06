package com.mondari;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

/**
 * <p>
 *
 * </p>
 *
 * @author limondar
 * @date 2020/12/4
 */
@Configuration
public class LocaleResolverConfig {
    @Bean
    LocaleResolver localeResolver() {
        MyLocaleResolver localeResolver = new MyLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }
}
