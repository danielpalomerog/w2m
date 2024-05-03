package com.knowmad.w2m.example.application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.knowmad.w2m.example.application.repository.StarshipRepository;
import com.knowmad.w2m.example.application.service.IRedisService;
import com.knowmad.w2m.example.domain.Starship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class StarshipServiceImpl implements
    com.knowmad.w2m.example.application.service.StarshipService {

  private final StarshipRepository starshipRepository;

  @Autowired
  private IRedisService redisService;

  @Override
  public Page<Starship> getAllStarshipService(PageRequest pageRequest) {
    Page<Starship> result = redisService.loadAllStarshipCache(pageRequest);

    if (result == null) {
      log.info("No existe información de todas las naves en cache");
      result = starshipRepository.getAllStarship(pageRequest);
      redisService.saveAllStarshipCache(pageRequest, result);

    } else {
      log.info("Información de todas las naves recuperada de cache");
    }

    return result;
  }

  @Override
  public Starship getStarshipByIdService(Long id) {
    Starship result = redisService.loadStarshipByIdCache(id);

    if (result == null) {
      log.info("No existe información para ese ID de nave en cache");
      result = starshipRepository.getStarshipById(id).orElseThrow();
      redisService.saveStarshipByIdCache(id, result);

    } else {
      log.info("Información de la nave por ID recuperada de cache");
    }

    return result;
  }

  @Override
  public List<Starship> getStarshipByNameService(String name) {
    List<Starship> result = redisService.loadStarshipByNameCache(name);

    if (result == null) {
      log.info("No existe información para ese nombre de nave en cache");
      result = starshipRepository.getStarshipByName(name).orElseThrow();
      redisService.saveStarshipByNameCache(name, result);

    } else {
      log.info("Información de la nave por nombre recuperada de cache");
    }

    return result;
  }

  @Override
  public Starship createStarshipService(Starship starship) {
    return starshipRepository.createStarship(starship);
  }

  @Override
  public Starship updateStarshipService(Starship starship) {
    return starshipRepository.updateStarship(starship);
  }

  @Override
  public void deleteStarshipService(Long id) {
    starshipRepository.deleteStarship(id);
  }


}
