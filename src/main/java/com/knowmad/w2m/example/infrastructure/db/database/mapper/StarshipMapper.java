package com.knowmad.w2m.example.infrastructure.db.database.mapper;

import java.util.ArrayList;
import java.util.List;

import com.knowmad.w2m.example.domain.Starship;
import com.knowmad.w2m.example.infrastructure.db.database.model.StarshipEntity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StarshipMapper {

  public static Starship toDomain(StarshipEntity entity) {
    return Starship.builder()
            .id(Math.toIntExact(entity.getId()))
            .name(entity.getName())
            .movie(entity.getMovie())
            .build();
  }

  public static List<Starship> toDomain(List<StarshipEntity> entity) {
    return new ArrayList<>(entity.stream().map(StarshipMapper::toDomain).toList());
  }

  public static StarshipEntity toEntity(Starship domain) {
    if (domain.getId() != null) {
      return StarshipEntity.builder()
          .id(Long.valueOf(domain.getId()))
          .name(domain.getName())
          .movie(domain.getMovie())
          .build();
    }
    return StarshipEntity.builder()
        .name(domain.getName())
        .movie(domain.getMovie())
        .build();
  }

}
