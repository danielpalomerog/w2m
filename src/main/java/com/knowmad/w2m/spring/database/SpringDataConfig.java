package com.knowmad.w2m.spring.database;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.knowmad.w2m.example.infrastructure.db.database.repository")
public class SpringDataConfig {
}
