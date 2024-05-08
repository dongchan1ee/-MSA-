package com.springtour.example.chapter03_3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

interface Formatter<T> {
    String of(T target);
}

@Slf4j
@Component
class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public String of(LocalDateTime target) {
        return Optional.ofNullable(target)
                .map(formatter::format)
                .orElse(null);
    }
}

@Slf4j
@SpringBootApplication
public class SpringBean02Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctxt = SpringApplication.run(SpringBean02Application.class);

        Formatter<LocalDateTime> formatter = ctxt.getBean("localDateTimeFormatter", Formatter.class);
        String date = formatter.of(LocalDateTime.of(2020, 12,24, 23, 59, 59));

        log.info("Date :"+ date);
        ctxt.close();
    }
}
