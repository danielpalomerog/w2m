package com.knowmad.w2m.example.infrastructure.rest.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knowmad.w2m.infrastructure.rest.spring.spec.LoginApi;
import com.knowmad.w2m.spring.security.JwtService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/")
@Validated
@AllArgsConstructor
public class LoginController implements LoginApi {

  private final JwtService jwtService;

  @Override
  public ResponseEntity<String> login(String username) {
    return ResponseEntity.ok(jwtService.generateToken(username));
  }
}
