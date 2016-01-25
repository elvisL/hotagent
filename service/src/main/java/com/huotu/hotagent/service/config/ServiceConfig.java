package com.huotu.hotagent.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 基础模块配置
 * Created by cwb on 2016/1/25.
 */
@Configuration
@ComponentScan("com.huotu.hotagent.service.service")
@EnableScheduling
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.huotu.hotagent.service.repository"
)
@ImportResource({"classpath:service_config_prod.xml", "classpath:service_config_dev.xml"})
public class ServiceConfig {
    /**
     * 可以替换为其他Password
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
