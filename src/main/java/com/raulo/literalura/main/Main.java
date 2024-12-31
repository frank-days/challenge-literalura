package com.raulo.literalura.main;

import com.raulo.literalura.helpers.FormatData;
import com.raulo.literalura.models.Autor;
import com.raulo.literalura.models.Datos;
import com.raulo.literalura.models.DatosLibro;
import com.raulo.literalura.models.Libro;
import com.raulo.literalura.repositories.AutorRepository;
import com.raulo.literalura.repositories.LibroRepository;
import com.raulo.literalura.services.GutendexApi;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private Scanner teclado = new Scanner(System.in);
    private GutendexApi api = new GutendexApi();
    private FormatData formatter = new FormatData();
    private final String URL_BASE = "https://gutendex.com/books/?search=";

    private Optional<Autor> autorEncontrado;
    private List<Libro> libros;
    private List<Autor> autores;

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Main(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {
        var opcion = -1;

        while (opcion != 0) {
            var menu = """
                1 - Buscar libro por titulo.
                2 - Mostrar libros registrados.
                3 - Mostrar autores registrados.
                4 - Mostrar autores vivos en un determinado año.
                5 - Mostrar libros por idioma.
                0 - Salir
                """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivos();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Ingresa el titulo del libro: ");
        var titulo = teclado.nextLine();

        var json = api.obtenerDatos(URL_BASE + titulo.replace(" ", "%20"));
        var resultados = formatter.format(json, Datos.class);

        Optional<DatosLibro> resultado = resultados.resultados().stream().findFirst();

        // Si no hay un libro, terminamos la funcion.
        if( resultado.isEmpty() ) {
            System.out.println("\nLibro no encontrado.\n");
            return;
        }

        System.out.println("\nCoincidencia: " + resultado.get());

        Libro libroEncontrado = new Libro( resultado.get() );
        Autor tempAutor = new Autor(resultado.get().autor().getFirst());

        // Buscamos si el libro ya esta registrado en la base de datos.
        if( !libroRepository.existsByTitulo(libroEncontrado.getTitulo()) ){
            autorEncontrado = autorRepository.findByNameContainsIgnoreCase(tempAutor.getName());

            // Si el Optional contiene un valor, significa que el autor ya tiene registro en la BD.
            if( autorEncontrado.isPresent() ){
                var a = autorEncontrado.get();

                libroEncontrado.setAutor(a);
                libroRepository.save(libroEncontrado);

                System.out.println("\nLibro almacenado con exito.\n");
            }
            // Si no, el autor aun no esta registrado.
            else{
                tempAutor.setLibros(libroEncontrado);
                autorRepository.save(tempAutor);

                System.out.println("\nLibro y Autor almacenados con exito.\n");
            }

        }
        else{
            System.out.println("\nEl libro ya esta registrado en la base de datos.\n");
        }

    }

    private void mostrarLibrosRegistrados() {
        libros = libroRepository.findAll();

        System.out.println("\nLibros Registrados\n");

        libros.forEach(System.out::println);

        System.out.println("\n");
    }

    private void mostrarAutoresRegistrados() {
        autores = autorRepository.findAll();

        System.out.println("\nAutores Registrados\n");

        autores.forEach(System.out::println);

        System.out.println("\n");
    }

    private void mostrarAutoresVivos() {
        System.out.println("\nDigita el año base: ");
        Integer fecha = teclado.nextInt();

        autores = autorRepository.findAll();

        System.out.println("\nAutores vivos en " + fecha);
        // Filtramos los resultados.
        autores.stream()
                .filter( a -> (fecha >= a.getBirthYear()) && (fecha <= a.getDeathYear()) )
                .forEach(System.out::println);

        System.out.println("\n");
    }

    private void mostrarLibrosPorIdioma() {
        var menu = """
            Elige un idioma:
            en - Ingles
            es - Español
            fr - Frances
            pt - Portugues
            """;
        System.out.println(menu);
        var idioma = teclado.nextLine();

        List<Libro> librosPorIdioma = libroRepository.findAllByIdioma(idioma);

        System.out.println("\n");
        librosPorIdioma.forEach(System.out::println);
        System.out.println("\n");
    }
}
