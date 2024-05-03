package com.knowmad.w2m.example.application.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.knowmad.w2m.example.domain.Starship;

public interface IRedisService {

  Page<Starship> loadAllStarshipCache(PageRequest pageRequest);

  Page<Starship> saveAllStarshipCache(PageRequest pageRequest, Page<Starship> starship);

  Starship loadStarshipByIdCache(Long id);

  Starship saveStarshipByIdCache(Long id, Starship starship);

  List<Starship> loadStarshipByNameCache(String name);

  List<Starship> saveStarshipByNameCache(String name, List<Starship> starships);

}
