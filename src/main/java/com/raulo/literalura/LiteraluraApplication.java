package com.raulo.literalura;

import com.raulo.literalura.main.Main;
import com.raulo.literalura.repositories.AutorRepository;
import com.raulo.literalura.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main( libroRepository, autorRepository );

		main.mostrarMenu();
	}
}
