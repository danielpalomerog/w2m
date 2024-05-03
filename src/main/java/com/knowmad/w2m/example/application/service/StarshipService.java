package com.knowmad.w2m.example.application.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.knowmad.w2m.example.domain.Starship;

public interface StarshipService {

  Page<Starship> getAllStarshipService(PageRequest pageRequest);

  Starship getStarshipByIdService(Long id);

  List<Starship> getStarshipByNameService(String name);

  Starship createStarshipService(Starship starship);

  Starship updateStarshipService(Starship starship);

  void deleteStarshipService(Long id);

}
