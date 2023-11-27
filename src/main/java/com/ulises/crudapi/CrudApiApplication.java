package com.ulises.crudapi;

import com.ulises.crudapi.entity.Inventario;
import com.ulises.crudapi.service.InventarioService;
import com.ulises.crudapi.service.InventarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class CrudApiApplication implements CommandLineRunner {

	/*@Autowired
	JdbcTemplate jdbcTemplate;*/

	public static void main(String[] args) {
		SpringApplication.run(CrudApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	/*String insertInventario = """
				INSERT INTO inventario(sku,nombre,cantidad) VALUES('101285', 'Bicicleta Bimex', 5);
				INSERT INTO inventario(sku,nombre,cantidad) VALUES('101286', 'Bicicleta Bimex', 3);
				""";

		jdbcTemplate.execute(insertInventario);*/
	}
}
