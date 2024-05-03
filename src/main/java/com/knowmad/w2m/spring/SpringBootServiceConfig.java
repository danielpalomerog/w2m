package com.knowmad.w2m.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.knowmad.w2m.example.application.repository.StarshipRepository;
import com.knowmad.w2m.example.application.service.IRedisService;
import com.knowmad.w2m.example.application.service.impl.IRedisServiceImpl;
import com.knowmad.w2m.example.application.service.impl.StarshipServiceImpl;
import com.knowmad.w2m.example.infrastructure.rest.spring.aspect.LogAspect;
//import com.knowmad.w2m.spring.aspect.LoggingAspect;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SpringBootServiceConfig {

  /*@Bean
  public InternalResourceViewResolver defaultViewResolver() {
    return new InternalResourceViewResolver();
  }*/

  /*@Bean
  public LoggingAspect getLoggingAspect() {
    return new LoggingAspect();
  }*/

  private final StarshipRepository starshipRepository;

  @Bean
  public com.knowmad.w2m.example.application.service.StarshipService starshipService() {
    return new StarshipServiceImpl(starshipRepository);
  }

  @Bean
  public LogAspect getLogAspect() {
    return new LogAspect();
  }

  @Bean
  public IRedisService getRedisService() {
    return new IRedisServiceImpl();
  }

  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return mapper;
  }

}
