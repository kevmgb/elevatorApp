package com.example.elevatorApp.config;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class HibernateConfig {
    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(@Lazy StatementInspector hibernateInterceptor) {
        return hibernateProperties -> {
            hibernateProperties.put("hibernate.session_factory.statement_inspector", hibernateInterceptor);
        };
    }
}
