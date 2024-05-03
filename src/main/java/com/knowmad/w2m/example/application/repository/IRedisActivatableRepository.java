package com.knowmad.w2m.example.application.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.knowmad.w2m.example.domain.Starship;

public interface IRedisActivatableRepository {

  Page<Starship> loadAllStarshipCache(PageRequest pageRequest, boolean isActive);

  Page<Starship> saveAllStarshipCache(PageRequest pageRequest, Page<Starship> starship, boolean isActive);

  void removeAllStarshipCache(PageRequest pageRequest, boolean isActive);

  void clearAllStarship();

  Starship loadStarshipByIdCache(Long id, boolean isActive);

  Starship saveStarshipByIdCache(Long id, Starship starship, boolean isActive);

  void removeStarshipByIdCache(Long id, boolean isActive);

  void clearAllStarshipById();

  List<Starship> loadStarshipByNameCache(String name, boolean isActive);

  List<Starship> saveStarshipByNameCache(String name, List<Starship> starships, boolean isActive);

  void removeStarshipByNameCache(String name, boolean isActive);

  void clearAllStarshipByName();

}
