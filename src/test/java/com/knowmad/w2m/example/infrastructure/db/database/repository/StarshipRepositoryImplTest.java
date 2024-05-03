package com.knowmad.w2m.example.infrastructure.db.database.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.knowmad.w2m.example.domain.Starship;
import com.knowmad.w2m.example.infrastructure.db.database.model.StarshipEntity;
import com.knowmad.w2m.example.infrastructure.db.database.repository.jpa.StarshipJpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StarshipRepositoryImpl.class})
@ExtendWith(SpringExtension.class)
class StarshipRepositoryImplTest {

  @MockBean
  private StarshipJpaRepository starshipJpaRepository;

  @Autowired
  private StarshipRepositoryImpl starshipRepositoryImpl;


  @Test
  void testGetAllStarship() {
    when(starshipJpaRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));

    Page<Starship> actualAllStarship = starshipRepositoryImpl.getAllStarship(null);

    verify(starshipJpaRepository).findAll((Pageable) isNull());
    assertTrue(actualAllStarship.toList().isEmpty());
  }

  @Test
  void testGetAllStarship2() {
    ArrayList<StarshipEntity> content = new ArrayList<>();
    content.add(StarshipEntity.builder().id(1L).movie("Movie").name("Name").build());
    PageImpl<StarshipEntity> pageImpl = new PageImpl<>(content);
    when(starshipJpaRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);

    Page<Starship> actualAllStarship = starshipRepositoryImpl.getAllStarship(null);

    verify(starshipJpaRepository).findAll((Pageable) isNull());
    List<Starship> toListResult = actualAllStarship.toList();
    assertEquals(1, toListResult.size());
    Starship getResult = toListResult.get(0);
    assertEquals("Movie", getResult.getMovie());
    assertEquals("Name", getResult.getName());
    assertEquals(1, getResult.getId().intValue());
  }

  @Test
  void testGetAllStarship3() {
    ArrayList<StarshipEntity> content = new ArrayList<>();
    content.add(StarshipEntity.builder().id(2L).movie("Movie2").name("Name2").build());
    content.add(StarshipEntity.builder().id(1L).movie("Movie").name("Name").build());
    PageImpl<StarshipEntity> pageImpl = new PageImpl<>(content);
    when(starshipJpaRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);

    Page<Starship> actualAllStarship = starshipRepositoryImpl.getAllStarship(null);

    verify(starshipJpaRepository).findAll((Pageable) isNull());
    List<Starship> toListResult = actualAllStarship.toList();
    assertEquals(2, toListResult.size());

    Starship getResult = toListResult.get(1);
    assertEquals("Movie", getResult.getMovie());
    assertEquals("Name", getResult.getName());

    Starship getResult2 = toListResult.get(0);
    assertEquals("Movie2", getResult2.getMovie());
    assertEquals("Name2", getResult2.getName());
    assertEquals(1, getResult.getId().intValue());
    assertEquals(2, getResult2.getId().intValue());
  }


  @Test
  void testGetAllStarship4() {
    when(starshipJpaRepository.findAll(Mockito.<Pageable>any())).thenThrow(new RuntimeException("foo"));

    assertThrows(RuntimeException.class, () -> starshipRepositoryImpl.getAllStarship(null));
    verify(starshipJpaRepository).findAll((Pageable) isNull());
  }

  @Test
  void testGetStarshipById() {
    Optional<StarshipEntity> ofResult = Optional.of(StarshipEntity.builder().id(1L).movie("Movie").name("Name").build());
    when(starshipJpaRepository.findById(Mockito.anyLong())).thenReturn(ofResult);

    Optional<Starship> actualStarshipById = starshipRepositoryImpl.getStarshipById(1L);

    verify(starshipJpaRepository).findById(eq(1L));
    Starship getResult = actualStarshipById.get();
    assertEquals("Movie", getResult.getMovie());
    assertEquals("Name", getResult.getName());
    assertEquals(1, getResult.getId().intValue());
  }

  @Test
  void testGetStarshipById2() {
    Optional<StarshipEntity> emptyResult = Optional.empty();
    when(starshipJpaRepository.findById(Mockito.anyLong())).thenReturn(emptyResult);

    Optional<Starship> actualStarshipById = starshipRepositoryImpl.getStarshipById(1L);

    verify(starshipJpaRepository).findById(eq(1L));
    assertFalse(actualStarshipById.isPresent());
    assertSame(emptyResult, actualStarshipById);
  }

  @Test
  void testGetStarshipByName() {
    Optional<List<StarshipEntity>> ofResult = Optional.of(new ArrayList<>());
    when(starshipJpaRepository.findByNameContainsIgnoreCase(Mockito.anyString())).thenReturn(ofResult);

    Optional<List<Starship>> actualStarshipByName = starshipRepositoryImpl.getStarshipByName("Name");

    verify(starshipJpaRepository).findByNameContainsIgnoreCase(eq("Name"));
    assertEquals(ofResult, actualStarshipByName);
  }

  @Test
  void testGetStarshipByName2() {
    ArrayList<StarshipEntity> starshipEntityList = new ArrayList<>();
    starshipEntityList.add(StarshipEntity.builder().id(1L).movie("Movie").name("Name").build());
    Optional<List<StarshipEntity>> ofResult = Optional.of(starshipEntityList);
    when(starshipJpaRepository.findByNameContainsIgnoreCase(Mockito.anyString())).thenReturn(ofResult);

    Optional<List<Starship>> actualStarshipByName = starshipRepositoryImpl.getStarshipByName("Name");

    verify(starshipJpaRepository).findByNameContainsIgnoreCase(eq("Name"));
    Starship getResult = actualStarshipByName.get().get(0);
    assertEquals("Movie", getResult.getMovie());
    assertEquals("Name", getResult.getName());
    assertEquals(1, getResult.getId().intValue());
  }

  @Test
  void testGetStarshipByName3() {
    ArrayList<StarshipEntity> starshipEntityList = new ArrayList<>();
    starshipEntityList.add(StarshipEntity.builder().id(2L).movie("Movie2").name("Name2").build());
    starshipEntityList.add(StarshipEntity.builder().id(1L).movie("Movie").name("Name").build());
    Optional<List<StarshipEntity>> ofResult = Optional.of(starshipEntityList);
    when(starshipJpaRepository.findByNameContainsIgnoreCase(Mockito.anyString())).thenReturn(ofResult);

    Optional<List<Starship>> actualStarshipByName = starshipRepositoryImpl.getStarshipByName("Name");

    verify(starshipJpaRepository).findByNameContainsIgnoreCase(eq("Name"));
    List<Starship> getResult = actualStarshipByName.get();
    Starship getResult2 = getResult.get(1);
    assertEquals("Movie", getResult2.getMovie());
    assertEquals("Name", getResult2.getName());
    Starship getResult3 = getResult.get(0);
    assertEquals("Movie2", getResult3.getMovie());
    assertEquals("Name2", getResult3.getName());
    assertEquals(1, getResult2.getId().intValue());
    assertEquals(2, getResult3.getId().intValue());
  }

  @Test
  void testGetStarshipByName4() {
    Optional<List<StarshipEntity>> emptyResult = Optional.empty();
    when(starshipJpaRepository.findByNameContainsIgnoreCase(Mockito.anyString())).thenReturn(
        emptyResult);

    Optional<List<Starship>> actualStarshipByName = starshipRepositoryImpl.getStarshipByName("Name");

    verify(starshipJpaRepository).findByNameContainsIgnoreCase(eq("Name"));
    assertFalse(actualStarshipByName.isPresent());
    assertSame(emptyResult, actualStarshipByName);
  }

  @Test
  void testGetStarshipByName5() {
    when(starshipJpaRepository.findByNameContainsIgnoreCase(Mockito.anyString())).thenThrow(new RuntimeException("foo"));

    assertThrows(RuntimeException.class, () -> starshipRepositoryImpl.getStarshipByName("Name"));
    verify(starshipJpaRepository).findByNameContainsIgnoreCase(eq("Name"));
  }


  @Test
  void testCreateStarship() {
    when(starshipJpaRepository.save(Mockito.any())).thenReturn(StarshipEntity.builder().id(1L).movie("Movie").name("Name").build());

    Starship actualCreateStarshipResult = starshipRepositoryImpl.createStarship(new Starship());

    verify(starshipJpaRepository).save(isA(StarshipEntity.class));
    assertEquals("Movie", actualCreateStarshipResult.getMovie());
    assertEquals("Name", actualCreateStarshipResult.getName());
    assertEquals(1, actualCreateStarshipResult.getId().intValue());
  }

  @Test
  void testUpdateStarship() {
    when(starshipJpaRepository.save(Mockito.any()))
        .thenReturn(StarshipEntity.builder().id(1L).movie("Movie").name("Name").build());

    Starship actualUpdateStarshipResult = starshipRepositoryImpl.updateStarship(new Starship());

    verify(starshipJpaRepository).save(isA(StarshipEntity.class));
    assertEquals("Movie", actualUpdateStarshipResult.getMovie());
    assertEquals("Name", actualUpdateStarshipResult.getName());
    assertEquals(1, actualUpdateStarshipResult.getId().intValue());
  }

  @Test
  void testDeleteStarship() {
    doNothing().when(starshipJpaRepository).deleteById(Mockito.anyLong());

    starshipRepositoryImpl.deleteStarship(1L);

    verify(starshipJpaRepository).deleteById(eq(1L));
  }

  @Test
  void testDeleteStarship2() {
    doThrow(new RuntimeException("foo")).when(starshipJpaRepository).deleteById(Mockito.anyLong());

    assertThrows(RuntimeException.class, () -> starshipRepositoryImpl.deleteStarship(1L));
    verify(starshipJpaRepository).deleteById(eq(1L));
  }
}
