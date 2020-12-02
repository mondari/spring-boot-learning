package com.mondari;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

import java.util.Locale;
import java.util.ResourceBundle;

@SpringBootApplication
public class I18nApplication {

    public static void main(String[] args) {
        SpringApplication.run(I18nApplication.class, args);
    }

    /**
     * Spring Boot 自动配置 MessageSource，类型为ResourceBundleMessageSource
     * @param messageSource
     * @return
     */
    @Bean
    CommandLineRunner run(MessageSource messageSource) {
        return args -> {
            Locale locale = Locale.getDefault();

            // 默认国际化文件的 basename 为 “messages”，可以在配置里修改为其他名称
            // 如果国际化文件不在 resources 资源文件夹的根目录，而是放在i18n文件夹下，则 basename 应该改为 “i18n.messages”
            System.out.println("----MessageSource----");
            System.out.println(messageSource.getMessage("greetings", new Object[]{"boys", "girls"}, locale));
            System.out.println(messageSource.getMessage("inquiry", null, locale));
            System.out.println(messageSource.getMessage("farewell", null, locale));
            System.out.println(messageSource.getMessage("error", null, locale));

            // Spring 的 MessageSource 其实是对 JDK 中的 ResourceBundle 进行了封装
            // 以下代码来自JDK官方教程：https://docs.oracle.com/javase/tutorial/i18n/intro/steps.html
            System.out.println("----ResourceBundle----");
            ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", locale);
            System.out.println(bundle.getString("greetings"));
            System.out.println(bundle.getString("inquiry"));
            System.out.println(bundle.getString("farewell"));
        };
    }

}
