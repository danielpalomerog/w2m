package com.knowmad.w2m.example.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.knowmad.w2m.example.domain.Starship;

public interface StarshipRepository {

  Page<Starship> getAllStarship(PageRequest pageRequest);

  Optional<Starship> getStarshipById(Long id);

  Optional<List<Starship>> getStarshipByName(String name);

  Starship createStarship(Starship starship);

  Starship updateStarship(Starship starship);

  void deleteStarship(Long id);

}
