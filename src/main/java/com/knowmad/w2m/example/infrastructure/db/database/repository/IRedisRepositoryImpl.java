package com.knowmad.w2m.example.infrastructure.db.database.repository;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.List;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.knowmad.w2m.example.application.repository.IRedisActivatableRepository;
import com.knowmad.w2m.example.application.repository.IRedisRepository;
import com.knowmad.w2m.example.domain.Starship;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class IRedisRepositoryImpl implements IRedisRepository {

  private boolean workCache;

  @Autowired
  ApplicationContext context;

  @Autowired
  DefaultListableBeanFactory beanFactory;

  private CacheManager cacheManager;

  private RedissonClient redissonClient;

  @Autowired
  private IRedisActivatableRepository redisActivatableRepository;

  @Value("#{new Boolean('${cache.active:true}')}")
  private Boolean flag;

  @PostConstruct
  void init() {
    workCache = redissonClient != null && redissonClient.getNodesGroup() != null &&
        redissonClient.getNodesGroup().pingAll();
  }

  @Scheduled(fixedDelay = 10000)
  void redisCheck() throws IOException {
    if (redissonClient != null) {

      if (!redissonClient.isShutdown() && !redissonClient.isShuttingDown()) {
        Boolean isWorking = redissonClient.getNodesGroup() != null &&
            redissonClient.getNodesGroup().pingAll();
        if (!isWorking) {
          log.warn("The cache with Redis is not working");
        } else if (!workCache) {
          cleanAllCaches();
        } else {
          log.info("The cache with Redis is ok");
        }
        workCache = isWorking;

      } else if (redissonClient.isShutdown()) {
        log.warn("The cache with Redis has its client as shutdown");
        redissonClient = null;

      } else if (redissonClient.isShuttingDown()) {
        log.warn("The cache with Redis has its client as shutting down");
      }

    } else {
      log.warn("The cache with Redis has its client as null");
      resetCacheBeans();
    }
  }

  @Override
  public Page<Starship> loadAllStarshipCache(PageRequest pageRequest) {
    return redisActivatableRepository.loadAllStarshipCache(pageRequest, workCache);
  }

  @Override
  public Page<Starship> saveAllStarshipCache(PageRequest pageRequest, Page<Starship> starship) {
    return redisActivatableRepository.saveAllStarshipCache(pageRequest, starship, workCache);
  }

  @Override
  public void removeAllStarshipCache(PageRequest pageRequest) {
    redisActivatableRepository.removeAllStarshipCache(pageRequest, workCache);
  }

  @Override
  public Starship loadStarshipByIdCache(Long id) {
    return redisActivatableRepository.loadStarshipByIdCache(id, workCache);
  }

  @Override
  public Starship saveStarshipByIdCache(Long id, Starship starship) {
    return redisActivatableRepository.saveStarshipByIdCache(id, starship, workCache);
  }

  @Override
  public void removeStarshipByIdCache(Long id) {
    redisActivatableRepository.removeStarshipByIdCache(id, workCache);
  }

  @Override
  public List<Starship> loadStarshipByNameCache(String name) {
    return redisActivatableRepository.loadStarshipByNameCache(name, workCache);
  }

  @Override
  public List<Starship> saveStarshipByNameCache(String name, List<Starship> starships) {
    return redisActivatableRepository.saveStarshipByNameCache(name, starships, workCache);
  }

  @Override
  public void removeStarshipByNameCache(String name) {
    redisActivatableRepository.removeStarshipByNameCache(name, workCache);
  }

  public  void cleanAllCaches() {
    try {
      redisActivatableRepository.clearAllStarship();
      redisActivatableRepository.clearAllStarshipById();
      redisActivatableRepository.clearAllStarshipByName();

      log.warn("The cache with Redis was cleared");
    } catch (Exception e) {
      log.error("Error cleaning cache!!");
    }
  }

  private void resetCacheBeans() {
    //Vuelve a crear el cliente de Redis
    beanFactory.destroySingleton("getRedissonClient");
    redissonClient = (RedissonClient) context.getBean("getRedissonClient");
    log.info(String.format("Instance of redissonClient: %1$s", redissonClient));

    //Vuelve a crear el manager de Redis
    beanFactory.destroySingleton("cacheManager");
    cacheManager = (CacheManager) context.getBean("cacheManager", redissonClient);
    log.info(String.format("Instance of cacheManager: %1$s", cacheManager));
  }

}
