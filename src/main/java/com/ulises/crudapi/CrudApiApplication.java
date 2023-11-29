package com.ulises.crudapi;

import com.ulises.crudapi.security.JWTTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
public class CrudApiApplication implements CommandLineRunner {
	private static final Logger LOG = LoggerFactory.getLogger(CrudApiApplication.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(CrudApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	String insertInventario = """
				INSERT INTO inventario(sku,nombre,cantidad) VALUES('101285', 'Bicicleta Bimex', 5);
				INSERT INTO inventario(sku,nombre,cantidad) VALUES('101286', 'Llanta R15-205/70', 3);
				INSERT INTO inventario(sku,nombre,cantidad) VALUES('224195', 'Trapeador', 3);
				INSERT INTO empleado(nombre,apellido,puesto) VALUES('Ulises','Trujillo','Arquitecto');
				""";

		jdbcTemplate.execute(insertInventario);
	}
}
