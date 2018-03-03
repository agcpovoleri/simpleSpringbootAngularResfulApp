package com.example.forumapp.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class I18nConfig {

    @Bean
    public LocalValidatorFactoryBean localValidatorFactory(MessageSource messageSource) {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();

        localValidatorFactoryBean.setValidationMessageSource(messageSource);

        return localValidatorFactoryBean;
    }
}
