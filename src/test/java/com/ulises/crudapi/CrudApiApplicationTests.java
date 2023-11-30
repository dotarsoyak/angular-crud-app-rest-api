package com.ulises.crudapi;

import com.ulises.crudapi.entity.Empleado;
import com.ulises.crudapi.model.EmpleadoRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class CrudApiApplicationTests {
	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	void debeRegresarForbiddenAlGrabarEmpleado() {
		var request = new HashMap<String, Object>();
		request.put("nombre", "Delia");
		request.put("apellido", "García");
		request.put("puesto", "Líder");

		ResponseEntity<String> response =
				testRestTemplate.postForEntity ("/empleado/add", request, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

}
