package com.raulo.literalura.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Long numeroDescargas;

    @ManyToOne
    private Autor autor;

    public Libro() { }

    public Libro(DatosLibro libro) {
        this.titulo = libro.titulo();
        this.idioma = libro.idiomas().getFirst();
        this.numeroDescargas = libro.numeroDescargas();
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Long numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Titulo = " + titulo +
                "\nAutor = " + autor.getName() +
                "\nIdioma = " + idioma +
                "\nNumero de descargas = " + numeroDescargas +
                "\n ----------------------------------------";
    }
}
