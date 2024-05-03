package com.knowmad.w2m.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Starship {

  @Id
  private Integer id;
  @Column(name = "name")
  private String name;
  @Column(name = "movie")
  private String movie;

}
