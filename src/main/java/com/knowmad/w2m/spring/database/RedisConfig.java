package com.knowmad.w2m.spring.database;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
@Getter
@Setter
@EnableScheduling
public class RedisConfig {

  @Value("${spring.data.redis.host}")
  private String host;

  @Value("${spring.data.redis.url}")
  private String url;

  @Value("${spring.data.redis.password}")
  private String pass;

  @Value("#{new Boolean('${cache.active:true}')}")
  private Boolean activeCache;

  private Boolean workCache;

  public static final  String CACHE_ALL_STARSHIP_SEARCH = "allStarshipSearch";
  public static final  String CACHE_STARSHIP_BY_ID_SEARCH = "starshipByIdSearch";
  public static final  String CACHE_STARSHIP_BY_NAME_SEARCH = "starshipByNameSearch";

  @Bean(destroyMethod = "shutdown")
  RedissonClient getRedissonClient() throws IOException {
    RedissonClient redissonClient = null;
    if (activeCache) {
      try {
        Config config = new Config();
        config.useReplicatedServers().addNodeAddress(url);
        if (StringUtils.isNotEmpty(pass)) {
          config.useReplicatedServers().setPassword(pass);
        }
        redissonClient = Redisson.create(config);
        log.info("El micro-servicio se ha conectado a redis con Redisson");

        workCache = true;
      } catch (RedisConnectionException e) {
        workCache = false;
        log.info("La cache utilizada es de memoria");
      }
    } else {
      workCache = false;
    }

    return redissonClient;
  }

  @Bean
  CacheManager cacheManager(RedissonClient redissonClient) {
    CacheManager result;
    SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
    RedissonSpringCacheManager redissonSpringCacheManager;
    if (workCache) {
      Map<String, CacheConfig> config = new HashMap<>();

      config.put(CACHE_ALL_STARSHIP_SEARCH, new CacheConfig(24 * 60 * 60 * 1_000L, 24 * 60 * 60 * 1_000L));
      config.put(CACHE_STARSHIP_BY_ID_SEARCH, new CacheConfig(24 * 60 * 60 * 1_000L, 24 * 60 * 60 * 1_000L));
      config.put(CACHE_STARSHIP_BY_NAME_SEARCH, new CacheConfig(24 * 60 * 60 * 1_000L, 24 * 60 * 60 * 1_000L));

      redissonSpringCacheManager = new RedissonSpringCacheManager(redissonClient, config);
      result = redissonSpringCacheManager;

    } else {
      result = simpleCacheManager;
    }

    return result;
  }

}
