package com.knowmad.w2m.example.infrastructure.db.database.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.knowmad.w2m.example.infrastructure.db.database.model.StarshipEntity;

public interface StarshipJpaRepository extends JpaRepository<StarshipEntity, Long> {

  Optional<List<StarshipEntity>> findByNameContainsIgnoreCase(String name);

}
