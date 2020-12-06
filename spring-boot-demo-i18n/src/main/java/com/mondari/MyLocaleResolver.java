package com.mondari;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * <p>
 * 自定义 LocaleResolver
 * </p>
 *
 * @author limondar
 * @date 2020/12/4
 */
@Data
public class MyLocaleResolver implements LocaleResolver {

    public static final String DEFAULT_PARAM_NAME = "lang";
    private String paramName = DEFAULT_PARAM_NAME;
    private Locale defaultLocale;

    /**
     * 从请求头和请求参数中解析国际化语言
     *
     * @param request
     * @return
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale requestParameterLocale = parseLocaleValue(request.getParameter(getParamName()));
        if (requestParameterLocale != null) {
            return requestParameterLocale;
        }

        Locale requestHeaderLocale = parseLocaleValue(request.getHeader(getParamName()));
        if (requestHeaderLocale != null) {
            return requestHeaderLocale;
        }

        if (request.getHeader(HttpHeaders.ACCEPT_LANGUAGE) != null) {
            return request.getLocale();
        }

        return defaultLocale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        throw new UnsupportedOperationException("Cannot set locale - use a different locale resolution strategy");
    }

    protected Locale parseLocaleValue(String localeValue) {
        return StringUtils.parseLocale(localeValue);
    }

}
