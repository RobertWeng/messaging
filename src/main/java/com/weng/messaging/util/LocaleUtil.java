package com.weng.messaging.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;

public class LocaleUtil {

    public static final Locale LOCALE_EN = new Locale.Builder().setLanguage(Locale.ENGLISH.getLanguage()).build();
    public static final Locale LOCALE_MS = new Locale.Builder().setLanguage("ms").build();

    public static Locale getLocale() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes instanceof ServletRequestAttributes) {
                HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                return Locale.forLanguageTag(request.getLocale().getLanguage());
            }
            return LOCALE_EN;
        } catch (Exception e) {
            return LOCALE_EN;
        }
    }
}
