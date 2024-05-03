package com.knowmad.w2m.example.infrastructure.rest.spring.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.knowmad.w2m.example.domain.Starship;
import com.knowmad.w2m.infrastructure.rest.spring.dto.StarshipCompletedDto;
import com.knowmad.w2m.infrastructure.rest.spring.dto.StarshipDto;
import com.knowmad.w2m.infrastructure.rest.spring.dto.StarshipResponseDto;

@Mapper(componentModel = "spring")
public interface StarshipRestMapper {

  StarshipResponseDto toRest(Starship domain);

  List<StarshipResponseDto> toRest(List<Starship> domain);

  List<StarshipResponseDto> toRest(Page<Starship> domain);

  Starship toDomain(StarshipDto domain);

  Starship toDomain(StarshipCompletedDto domain);

}
