package com.knowmad.w2m.example.infrastructure.db.database.repository;

import javax.cache.annotation.CacheRemoveAll;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.knowmad.w2m.example.application.repository.IRedisActivatableRepository;
import com.knowmad.w2m.example.domain.Starship;
import com.knowmad.w2m.spring.database.RedisConfig;

@Repository
public class RedisActivatableRepository implements IRedisActivatableRepository {

  @Override
  @Cacheable(value = RedisConfig.CACHE_ALL_STARSHIP_SEARCH, key = "#key", condition = "#isActive")
  public Page<Starship> loadAllStarshipCache(PageRequest pageRequest, boolean isActive) {
    return null;
  }

  @Override
  @CachePut(value = RedisConfig.CACHE_ALL_STARSHIP_SEARCH, key = "#key", condition = "#isActive")
  public Page<Starship> saveAllStarshipCache(PageRequest pageRequest, Page<Starship> starship,
      boolean isActive) {
    return starship;
  }

  @Override
  @CacheEvict(value = RedisConfig.CACHE_ALL_STARSHIP_SEARCH, key = "#key", condition = "#isActive")
  public void removeAllStarshipCache(PageRequest pageRequest, boolean isActive) {

  }

  @Override
  @CacheRemoveAll(cacheName = RedisConfig.CACHE_ALL_STARSHIP_SEARCH)
  public void clearAllStarship() {

  }

  @Override
  @Cacheable(value = RedisConfig.CACHE_STARSHIP_BY_ID_SEARCH, key = "#key", condition = "#isActive")
  public Starship loadStarshipByIdCache(Long id, boolean isActive) {
    return null;
  }

  @Override
  @CachePut(value = RedisConfig.CACHE_STARSHIP_BY_ID_SEARCH, key = "#key", condition = "#isActive")
  public Starship saveStarshipByIdCache(Long id, Starship starship, boolean isActive) {
    return starship;
  }

  @Override
  @CacheEvict(value = RedisConfig.CACHE_STARSHIP_BY_ID_SEARCH, key = "#key", condition = "#isActive")
  public void removeStarshipByIdCache(Long id, boolean isActive) {

  }

  @Override
  @CacheRemoveAll(cacheName = RedisConfig.CACHE_STARSHIP_BY_ID_SEARCH)
  public void clearAllStarshipById() {

  }

  @Override
  @Cacheable(value = RedisConfig.CACHE_STARSHIP_BY_NAME_SEARCH, key = "#key", condition = "#isActive")
  public List<Starship> loadStarshipByNameCache(String name, boolean isActive) {
    return null;
  }

  @Override
  @CachePut(value = RedisConfig.CACHE_STARSHIP_BY_NAME_SEARCH, key = "#key", condition = "#isActive")
  public List<Starship> saveStarshipByNameCache(String name, List<Starship> starships,
      boolean isActive) {
    return starships;
  }

  @Override
  @CacheEvict(value = RedisConfig.CACHE_STARSHIP_BY_NAME_SEARCH, key = "#key", condition = "#isActive")
  public void removeStarshipByNameCache(String name, boolean isActive) {

  }

  @Override
  @CacheRemoveAll(cacheName = RedisConfig.CACHE_STARSHIP_BY_NAME_SEARCH)
  public void clearAllStarshipByName() {

  }

}
