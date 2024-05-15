package com.springtour.example.chapter03_6;

import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.format.Formatter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Slf4j
@SpringBootApplication
public class SpringBean05Application {
    public static void main(String[] args) throws InterruptedException{
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBean05Application.class, args);
        ThreadPoolTaskExecutor taskExecutor = applicationContext.getBean(ThreadPoolTaskExecutor.class);

        final String dateString = "2020-12-24T23:59:59-08:00";
        for (int i = 0; i < 100; i++) {
            taskExecutor.submit(() -> {
                try {
                    CustomDateFormatter formatter = applicationContext.getBean("singletonDateFormatter", CustomDateFormatter.class);
                    log.info("Date : {}, hashCode : {}", formatter.parse(dateString, Locale.getDefault()), formatter.hashCode());
                } catch (Exception e) {
                    log.error("error to parse", e);
                }
                    });
        }
    }

    @Bean
    @Scope("prototype")
    public CustomDateFormatter singletonDateFormatter() {
        return new CustomDateFormatter("yyyy-MM-dd'T'HH:mm:ss");
    }

}

class CustomDateFormatter implements Formatter<Date> {

    private SimpleDateFormat sdf;

    public CustomDateFormatter(String pattern) {
        if(StringUtils.isEmpty(pattern)) {
            throw new IllegalStateException("Pattern is empty");
        }

        this.sdf = new SimpleDateFormat(pattern);
    }

    @Override
    public String print(Date date, Locale locale) {
        return sdf.format(date);
    }

    @Override
    public Date parse(String dateString, Locale locale) throws ParseException {
        return sdf.parse(dateString);
    }
}


