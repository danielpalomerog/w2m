package com.knowmad.w2m.example.application.service.impl;

import java.util.List;

import org.redisson.client.RedisConnectionException;
import org.redisson.client.RedisTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Service;

import com.knowmad.w2m.example.application.repository.IRedisRepository;
import com.knowmad.w2m.example.application.service.IRedisService;
import com.knowmad.w2m.example.domain.Starship;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IRedisServiceImpl implements IRedisService {

  @Autowired
  private IRedisRepository redisRepository;

  @Override
  public Page<Starship> loadAllStarshipCache(PageRequest pageRequest) {
    Page<Starship> resultado = null;
    try {
      resultado = redisRepository.loadAllStarshipCache(pageRequest);
      if (null == resultado) {
        redisRepository.removeAllStarshipCache(pageRequest);
      }
      getReadisReadingCompleted();
    } catch (RedisConnectionException | RedisConnectionFailureException | RedisTimeoutException e) {
      getRedisReadFailure();
    } catch (Exception e2) {
      getRedisError("Fallo de lectura en redis: {}", e2);
    }

    return resultado;
  }

  @Override
  public Page<Starship> saveAllStarshipCache(PageRequest pageRequest, Page<Starship> starship) {
    Page<Starship> resultado = null;

    try {
      if (starship != null) {
        resultado = redisRepository.saveAllStarshipCache(pageRequest, starship);
        getRedisWritingCompleted();
      }
    } catch (RedisConnectionException | RedisConnectionFailureException | RedisTimeoutException e) {
      getRedisWritingFailed();
    } catch (Exception e2) {
      getRedisError("Fallo de escritura en redis: {}", e2);
    }

    return resultado;
  }

  @Override
  public Starship loadStarshipByIdCache(Long id) {
    Starship resultado = null;
    try {
      resultado = redisRepository.loadStarshipByIdCache(id);
      if (null == resultado) {
        redisRepository.removeStarshipByIdCache(id);
      }
      getReadisReadingCompleted();
    } catch (RedisConnectionException | RedisConnectionFailureException | RedisTimeoutException e) {
      getRedisReadFailure();
    } catch (Exception e2) {
      getRedisError("Fallo de lectura en redis: {}", e2);
    }

    return resultado;
  }

  @Override
  public Starship saveStarshipByIdCache(Long id, Starship starship) {
    Starship resultado = null;

    try {
      if (starship != null) {
        resultado = redisRepository.saveStarshipByIdCache(id, starship);
        getRedisWritingCompleted();
      }
    } catch (RedisConnectionException | RedisConnectionFailureException | RedisTimeoutException e) {
      getRedisWritingFailed();
    } catch (Exception e2) {
      getRedisError("Fallo de escritura en redis: {}", e2);
    }

    return resultado;
  }

  @Override
  public List<Starship> loadStarshipByNameCache(String name) {
    List<Starship> resultado = null;
    try {
      resultado = redisRepository.loadStarshipByNameCache(name);
      if (null == resultado) {
        redisRepository.removeStarshipByNameCache(name);
      }
      getReadisReadingCompleted();
    } catch (RedisConnectionException | RedisConnectionFailureException | RedisTimeoutException e) {
      getRedisReadFailure();
    } catch (Exception e2) {
      getRedisError("Fallo de lectura en redis: {}", e2);
    }

    return resultado;
  }

  @Override
  public List<Starship> saveStarshipByNameCache(String name, List<Starship> starships) {
    List<Starship> resultado = null;

    try {
      if (starships != null) {
        resultado = redisRepository.saveStarshipByNameCache(name, starships);
        getRedisWritingCompleted();
      }
    } catch (RedisConnectionException | RedisConnectionFailureException | RedisTimeoutException e) {
      getRedisWritingFailed();
    } catch (Exception e2) {
      getRedisError("Fallo de escritura en redis: {}", e2);
    }

    return resultado;
  }

  private static void getRedisReadFailure() {
    log.error("Fallo de lectura en redis");
  }

  private static void getReadisReadingCompleted() {
    log.debug("Lectura en redis completa");
  }

  private static void getRedisError(String s, Exception e2) {
    log.error(s, e2.getMessage());
  }

  private static void getRedisWritingCompleted() {
    log.debug("Escritura en redis completa");
  }

  private static void getRedisWritingFailed() {
    log.error("Fallo de escritura en redis");
  }

}
