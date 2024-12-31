package com.raulo.literalura.repositories;

import com.raulo.literalura.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    boolean existsByTitulo(String titulo);

    List<Libro> findAllByIdioma(String idioma);
}
