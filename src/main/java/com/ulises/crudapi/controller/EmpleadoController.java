package com.ulises.crudapi.controller;

import com.ulises.crudapi.entity.Empleado;
import com.ulises.crudapi.model.EmpleadoRequest;
import com.ulises.crudapi.service.EmpleadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/empleado")
public class EmpleadoController {
    private static final Logger LOG = LoggerFactory.getLogger(EmpleadoController.class);

    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping("/add")
    public ResponseEntity<Empleado> save(@RequestBody EmpleadoRequest empleadoRequest
            , UriComponentsBuilder ucb) {

        LOG.info("Objeto EmpleadoRequest: {}", empleadoRequest);
        var createdEmpleado = this.empleadoService.save(empleadoRequest);

        URI location = ucb.path("/empleado/add/{id}")
                .buildAndExpand(createdEmpleado.getIdEmpleado())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/hi")
    public String hello(){
        return "Hello";
    }

}
