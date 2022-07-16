package com.druwa.config.all.locale;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class LocaleConfig {

    @PostConstruct
    public void init() {
        // Locale.setDefault(Locale.KOREA);
        // TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}