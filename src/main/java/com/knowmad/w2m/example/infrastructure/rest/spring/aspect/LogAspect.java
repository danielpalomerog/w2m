package com.knowmad.w2m.example.infrastructure.rest.spring.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
public class LogAspect {

  @Before("execution(* com.knowmad.w2m.example.application.service.impl"
      + ".StarshipServiceImpl.getStarshipByIdService(Long)) && args(id)")
  public void checkNegativeNumber(Long id) {
    if (id < 0) {
      log.error("Se ha pasado un número negativo como parámetro: {}", id);
    }
  }

}
