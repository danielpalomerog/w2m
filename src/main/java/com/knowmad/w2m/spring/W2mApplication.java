package com.knowmad.w2m.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = { "com.knowmad.w2m.*.infrastructure", "com.knowmad.w2m.spring" },
    exclude = ZipkinAutoConfiguration.class)
@EntityScan(basePackages = "com.knowmad.w2m.example.infrastructure.db.database.model")
public class W2mApplication {
  public static void main(String[] args) {
    SpringApplication.run(W2mApplication.class, args);
  }

}
