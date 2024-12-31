package com.raulo.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutor(
    @JsonAlias("name") String nombre,
    @JsonAlias("birth_year") Long birthYear,
    @JsonAlias("death_year") Long deathYear
) {
}
