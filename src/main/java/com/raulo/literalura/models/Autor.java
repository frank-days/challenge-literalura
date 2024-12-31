package com.raulo.literalura.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(unique = true)
    private String name;

    private Long birthYear;
    private Long deathYear;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() { }

    public Autor(DatosAutor autor) {
        this.name = autor.nombre();
        this.birthYear = autor.birthYear();
        this.deathYear = autor.deathYear();
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Long birthYear) {
        this.birthYear = birthYear;
    }

    public Long getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Long deathYear) {
        this.deathYear = deathYear;
    }

    public List<String> getLibros() {
        return libros.stream()
                .map(Libro::getTitulo)
                .toList();
    }

    public void setLibros(Libro libro) {
        libros = new ArrayList<>();
        libros.add(libro);
        libro.setAutor(this);
    }

    @Override
    public String toString() {
        return "Nombre = " + name +
                "\nAño de Nacimiento = " + birthYear +
                "\nAño de Fallecimiento = " + deathYear +
                "\nLibros = " + libros +
                "\n ----------------------------------------";
    }
}
