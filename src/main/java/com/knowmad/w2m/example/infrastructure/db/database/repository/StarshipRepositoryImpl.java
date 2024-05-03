package com.knowmad.w2m.example.infrastructure.db.database.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.knowmad.w2m.example.application.repository.StarshipRepository;
import com.knowmad.w2m.example.domain.Starship;
import com.knowmad.w2m.example.infrastructure.db.database.mapper.StarshipMapper;
import com.knowmad.w2m.example.infrastructure.db.database.repository.jpa.StarshipJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StarshipRepositoryImpl implements StarshipRepository {

  private final StarshipJpaRepository starshipJpaRepository;

  @Override
  public Page<Starship> getAllStarship(PageRequest pageRequest) {
    return starshipJpaRepository.findAll(pageRequest).map(StarshipMapper::toDomain);
  }

  @Override
  public Optional<Starship> getStarshipById(Long id) {
    return starshipJpaRepository.findById(id).map(StarshipMapper::toDomain);
  }

  @Override
  public Optional<List<Starship>> getStarshipByName(String name) {
    return starshipJpaRepository.findByNameContainsIgnoreCase(name).map(StarshipMapper::toDomain);
  }

  @Override
  public Starship createStarship(Starship starship) {
    return StarshipMapper.toDomain(starshipJpaRepository.save(StarshipMapper.toEntity(starship)));
  }

  @Override
  public Starship updateStarship(Starship starship) {
    return StarshipMapper.toDomain(starshipJpaRepository.save(StarshipMapper.toEntity(starship)));
  }

  @Override
  public void deleteStarship(Long id) {
    try {
      starshipJpaRepository.deleteById(id);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
