package com.knowmad.w2m.example.application.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.knowmad.w2m.example.domain.Starship;

public interface IRedisRepository {

  Page<Starship> loadAllStarshipCache(PageRequest pageRequest);

  Page<Starship> saveAllStarshipCache(PageRequest pageRequest, Page<Starship> starship);

  void removeAllStarshipCache(PageRequest pageRequest);

  Starship loadStarshipByIdCache(Long id);

  Starship saveStarshipByIdCache(Long id, Starship starship);

  void removeStarshipByIdCache(Long id);

  List<Starship> loadStarshipByNameCache(String name);

  List<Starship> saveStarshipByNameCache(String name, List<Starship> starships);

  void removeStarshipByNameCache(String name);

}
