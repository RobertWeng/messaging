package com.weng.messaging.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil implements ApplicationContextAware {

    @Autowired
    private static ResourceBundleMessageSource messageSource;

    @Override
    public void setApplicationContext(ApplicationContext ac) {
        MessageUtil.messageSource = ac.getBean(ResourceBundleMessageSource.class);
    }

    public static String getMessage(String code, Object... args) {
        if (messageSource == null) return code;
        return messageSource.getMessage(code, args, LocaleUtil.getLocale());
    }

}
