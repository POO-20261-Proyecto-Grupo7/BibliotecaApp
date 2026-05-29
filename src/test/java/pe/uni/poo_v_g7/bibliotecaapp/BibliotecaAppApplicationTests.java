package pe.uni.poo_v_g7.bibliotecaapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class BibliotecaAppApplicationTests {

	@Test
	void contextLoads() {
	}

}
