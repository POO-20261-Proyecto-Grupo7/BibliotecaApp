package pe.uni.poo_v_g7.bibliotecaapp;

import org.springframework.boot.SpringApplication;

public class TestBibliotecaAppApplication {

	public static void main(String[] args) {
		SpringApplication.from(BibliotecaAppApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
