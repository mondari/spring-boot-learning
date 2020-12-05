# spring-boot-i18n

**国际化**

国际化的底层是使用 JDK 的 ResourceBundle。

国际化相关的类：

- MessageSource (Bean)：国际化核心类，默认为 ResourceBundleMessageSource，其内部使用 JDK 中的 ResourceBundle 对信息进行国际化。
- LocaleResolver (Bean)：解析国际化语言，给 MessageSource 用。默认实现为 AcceptHeaderLocaleResolver，从请求头中解析国际化语言，另外还有 Cookie、Session、FixedLocaleResolver。如果想要从请求参数中解析国际化语言，可以搭配下面的拦截器。
- LocaleChangeInterceptor：从请求参数中解析国际化语言，然后通过 localeResolver.setLocale 方法设置进 LocacleResolver 中。但是需要注意的是不能搭配 AcceptHeaderLocaleResolver，因为其 setLocale 方法无效。
- LocaleContextResolver：继承 LocaleResolver，对其做增强，加入区域和时区信息。注意前者是在 spring-web 包中，后者在 spring-webmvc 包中。
- LocaleContextHolder：保存国际化上下文，在视图渲染的时候会用到。
- org.springframework.web.servlet.DispatcherServlet#buildLocaleContext：设置 LocaleContextHolder。注意在过滤器中设置 LocaleContextHolder 是没有用的，因为这里会覆盖掉。

建议使用不带区域的 messages.properties，如 messages_en.properties 和 messages_zh.properties，这样不仅 en 和 zh 能国际化，en_US、zh_CN 也能国际化。

