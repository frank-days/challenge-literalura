package com.raulo.literalura.repositories;

import com.raulo.literalura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNameContainsIgnoreCase(String nombreAutor);
}
