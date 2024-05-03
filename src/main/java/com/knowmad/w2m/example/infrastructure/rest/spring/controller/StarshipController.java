package com.knowmad.w2m.example.infrastructure.rest.spring.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.knowmad.w2m.example.infrastructure.rest.spring.mapper.StarshipRestMapper;
import com.knowmad.w2m.infrastructure.rest.spring.dto.StarshipCompletedDto;
import com.knowmad.w2m.infrastructure.rest.spring.dto.StarshipDto;
import com.knowmad.w2m.infrastructure.rest.spring.dto.StarshipResponseDto;
import com.knowmad.w2m.example.application.service.StarshipService;
import com.knowmad.w2m.infrastructure.rest.spring.spec.StarshipApi;
import com.knowmad.w2m.spring.security.JwtService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/")
@Validated
@AllArgsConstructor
public class StarshipController implements StarshipApi {

  private final StarshipService starshipService;

  private final StarshipRestMapper starshipRestMapper;

  private final JwtService jwtService;

  @Override
  public ResponseEntity<List<StarshipResponseDto>> getAllStarship(Long page, Long limit) {
    String authorization = getAuthorization();

    if (jwtService.validateToken(authorization)) {
      log.info("Start get getAllStarship page: {}, limit {}", page, limit);

      var pageRequest = PageRequest.of(Math.toIntExact(page), Math.toIntExact(limit));

      var starship =  starshipService.getAllStarshipService(pageRequest);

      log.info("End get getAllStarship pageRequest: {}", pageRequest);
      return ResponseEntity.ok(starshipRestMapper.toRest(starship));
    }
    throw new RuntimeException("No tiene permisos para acceder");
  }

  @Override
  public ResponseEntity<StarshipResponseDto> getStarshipById(Long id) {

    String authorization = getAuthorization();

    if (jwtService.validateToken(authorization)) {
      log.info("Start get getExampleById {}", id);

      var starship =  starshipService.getStarshipByIdService(id);

      log.info("End get getExampleById {}", id);
      return ResponseEntity.ok(starshipRestMapper.toRest(starship));
    }

    throw new RuntimeException("No tiene permisos para acceder");
  }

  @Override
  public ResponseEntity<List<StarshipResponseDto>> getStarshipByName(String name) {

    String authorization = getAuthorization();

    if (jwtService.validateToken(authorization)) {
      log.info("Start get getExampleByName {}", name);

      var starshipList =  starshipService.getStarshipByNameService(name);

      log.info("End get getExampleByName {}", name);
      return ResponseEntity.ok(starshipRestMapper.toRest(starshipList));
    }

    throw new RuntimeException("No tiene permisos para acceder");
  }

  @Override
  public ResponseEntity<StarshipResponseDto> createStarship(StarshipDto starshipDto) {

    String authorization = getAuthorization();

    if (jwtService.validateToken(authorization)) {
      log.info("Start get createStarship {}", starshipDto);

      var starship =  starshipService.createStarshipService(starshipRestMapper.toDomain(starshipDto));

      log.info("End get createStarship {}", starshipDto);
      return ResponseEntity.ok(starshipRestMapper.toRest(starship));
    }

    throw new RuntimeException("No tiene permisos para acceder");
  }

  @Override
  public ResponseEntity<StarshipResponseDto> updateStarship(StarshipCompletedDto starshipDto) {

    String authorization = getAuthorization();

    if (jwtService.validateToken(authorization)) {
      log.info("Start get updateStarship {}", starshipDto);

      var starship =  starshipService.updateStarshipService(starshipRestMapper.toDomain(starshipDto));

      log.info("End get updateStarship {}", starshipDto);
      return ResponseEntity.ok(starshipRestMapper.toRest(starship));
    }

    throw new RuntimeException("No tiene permisos para acceder");
  }

  @Override
  public ResponseEntity<Void> deleteStarship(Long id) {

    String authorization = getAuthorization();

    if (jwtService.validateToken(authorization)) {
      log.info("Start get deleteStarship {}", id);

      starshipService.deleteStarshipService(id);

      log.info("End get deleteStarship {}", id);
      return ResponseEntity.noContent().build();
    }

    throw new RuntimeException("No tiene permisos para acceder");
  }

  private static String getAuthorization() {
    return ((ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes())
        .getRequest().getHeader("Authorization").replace("Bearer ", "");
  }
}
