package com.ulises.crudapi.controller;

import com.ulises.crudapi.entity.Empleado;
import com.ulises.crudapi.model.EmpleadoRequest;
import com.ulises.crudapi.repository.EmpleadoRepository;
import com.ulises.crudapi.response.ConsultarEmpleadoResponse;
import com.ulises.crudapi.response.FailResponse;
import com.ulises.crudapi.service.EmpleadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

import static com.ulises.crudapi.messages.PolizaMessage.ERROR_CONSULTAR_EMPLEADO;
import static com.ulises.crudapi.messages.PolizaMessage.ERROR_GRABADO_POLIZA;

@RestController
@RequestMapping(path = "/api/v1/empleado")
public class EmpleadoController {
    private static final Logger LOG = LoggerFactory.getLogger(EmpleadoController.class);

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @PostMapping("/add")
    public ResponseEntity<Empleado> save(@RequestBody EmpleadoRequest empleadoRequest
            , UriComponentsBuilder ucb) {
        try{
            LOG.info("Inicia grabado de empleado: {}", empleadoRequest);
            var createdEmpleado = this.empleadoService.save(empleadoRequest);

            URI location = ucb.path("/empleado/add/{id}")
                    .buildAndExpand(createdEmpleado.getIdEmpleado())
                    .toUri();

            return ResponseEntity.created(location).build();
        }catch(Exception ex){
            LOG.error("Ocurrió un error al grabar el empleado: {}", ex.toString());
        }finally{
            LOG.info("Termina grabado de empleado.");
        }

        return ResponseEntity.ok(new Empleado());
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> all(){
        try{
            LOG.info("Inicia consulta de empleados.");
            var empleados = this.empleadoRepository.findAll();

            var response =
                    ConsultarEmpleadoResponse.buildFromEntityList(empleados);

            return ResponseEntity.ok(response);
        }catch(Exception ex) {
            LOG.error(ERROR_CONSULTAR_EMPLEADO, ex.toString());
            return ResponseEntity.ok(FailResponse.build(ERROR_CONSULTAR_EMPLEADO));
        }finally{
            LOG.info("Termina consulta de empleados.");
        }
    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<Map<String, Object>> byName(@PathVariable("name") String name){
        try{
            LOG.info("Inicia consulta de empleados por nombre.");
            var empleados = this.empleadoRepository.findAllByNombreContainsIgnoreCase(name);

            var response =
                    ConsultarEmpleadoResponse.buildFromEntityList(empleados);

            return ResponseEntity.ok(response);
        }catch(Exception ex) {
            LOG.error(ERROR_CONSULTAR_EMPLEADO, ex.toString());
            return ResponseEntity.ok(FailResponse.build(ERROR_CONSULTAR_EMPLEADO));
        }finally{
            LOG.info("Termina consulta de empleados por nombre.");
        }
    }


}
