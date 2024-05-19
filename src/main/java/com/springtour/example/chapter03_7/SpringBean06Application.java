package com.springtour.example.chapter03_7;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SpringBean06Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctxt = SpringApplication.run(SpringBean06Application.class);
        ctxt.close();
    }

    @Bean(initMethod = "init", destroyMethod = "clear")
    public LifeCycleComponent lifeCycleComponent() {
        return new LifeCycleComponent();
    }

    @Bean
    public BeanPostProcessor beanPostProcessor() {
        return new PrintableBeanPostProcessor();
    }
}

@Slf4j
class LifeCycleComponent implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        log.error("afterPropertiesSet from InitializingBean");
    }

    @Override
    public void destroy() throws Exception {
        log.error("destroy from DisposableBean");
    }

    public void init() {
        log.error("customized init method");
    }

    public void clear() {
        log.error("customized destroy method");
    }
}

@Slf4j
class PrintableBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if("lifeCycleComponent".equals(beanName)) {
            log.error("Called postProcessBeforeInitialization() for : {}", beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if("lifeCycleComponent".equals(beanName)) {
            log.error("Called postProcessAfterInitialization() for : {}", beanName);
        }
        return bean;
    }
}
