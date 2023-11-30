package com.ulises.crudapi.controller;

import com.ulises.crudapi.model.EmpleadoActualizaRequest;
import com.ulises.crudapi.model.PolizaRequest;
import com.ulises.crudapi.repository.EmpleadoRepository;
import com.ulises.crudapi.repository.PolizaRepository;
import com.ulises.crudapi.response.FailResponse;
import com.ulises.crudapi.response.OkResponse;
import com.ulises.crudapi.service.PolizaService;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

import static com.ulises.crudapi.messages.PolizaMessage.*;

@RestController
@RequestMapping(path = "/api/v1/poliza")
public class PolizaController {
    private static final Logger LOG = LoggerFactory.getLogger(PolizaController.class);

    @Autowired
    private EntityManager em;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PolizaService polizaService;
    @Autowired
    private PolizaRepository polizaRepository;
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> save(@RequestBody PolizaRequest polizaRequest
            , UriComponentsBuilder ucb) {

        try{
            LOG.info("Inicia grabado de poliza: {}.", polizaRequest);
            var grabadoResponse = this.polizaService.save(polizaRequest);

            URI location = ucb.path("/poliza/add/{id}")
                    .buildAndExpand(polizaRequest.getIdPoliza())
                    .toUri();

            return ResponseEntity.ok(grabadoResponse);
        }catch(Exception ex){
            LOG.error("Ha ocurrido un error al intentar grabar la póliza {}", ex.toString());
            return ResponseEntity.ok(FailResponse.build(ERROR_GRABADO_POLIZA));
        }finally{
            LOG.info("Termina grabado de póliza.");
        }

    }

    @GetMapping("/update/{idPoliza}/{idEmpleado}/{nombreEmpleado}")
    public ResponseEntity<Map<String, Object>> actualizarDatosEmpleado(
            @PathVariable("idPoliza") String idPoliza
            , @PathVariable("idEmpleado") String idEmpleado
            , @PathVariable("nombreEmpleado") String nombreEmpleado
    ){
        try{
            LOG.info("Inicia actualización de póliza {} por idEmpleado: {}.", idPoliza, idEmpleado);
            var polizaToUpdate =
                    this.polizaRepository.findById(Long.parseLong(idPoliza));

            if(!this.polizaRepository.existsById(Long.parseLong(idPoliza))){
                LOG.error("La póliza: {} no existe.", idPoliza);
                return ResponseEntity.ok(FailResponse.build(ERROR_ACTUALIZAR_POLIZA));
            }

            if(!this.empleadoRepository.existsById(Long.parseLong(idEmpleado))){
                LOG.error("El empleado: {} no existe.", idPoliza);
                return ResponseEntity.ok(FailResponse.build(ERROR_ACTUALIZAR_POLIZA));
            }

            if(polizaToUpdate.isPresent()){
                var empleadoActualizaRequest =
                        EmpleadoActualizaRequest.map(
                                idPoliza, idEmpleado, nombreEmpleado
                        );

                this.polizaService.actualizarPolizaDatosEmpleado(empleadoActualizaRequest, polizaToUpdate.get());
            }

            return ResponseEntity.ok(OkResponse.build("Se actualizó correctamente la póliza " + idPoliza));
        }catch(Exception e){
            LOG.error("Ha ocurrido un error al intentar actualizar la póliza {}", e.toString());
            return ResponseEntity.ok(FailResponse.build(ERROR_ACTUALIZAR_POLIZA));
        }finally{
            LOG.info("Termina actualización de póliza por idEmpleado.");
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id){
        try{
            LOG.info("Inicia eliminación de póliza: {}.", id);
            if(this.polizaRepository.existsById(id)){
                this.polizaService.cancelarPoliza(id);
                return ResponseEntity.ok(OkResponse.build("Se eliminó correctamente la poliza: " + id));
            }

            LOG.error("La póliza: {} no existe.", id);
            return ResponseEntity.ok(FailResponse.build(ERROR_ELIMINAR_POLIZA));
        }catch(Exception ex){
            LOG.error("Ocurrió un error al eliminar la póliza: " + ex.toString());
            return ResponseEntity.ok(FailResponse.build(ERROR_ELIMINAR_POLIZA));
        }finally {
            LOG.info("Termina eliminación de póliza.");
        }
    }

    /*Consultar poliza*/
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable("id") Long id){
        try{
            LOG.info("Inicia consulta de póliza por idPoliza: {}.", id);
            if(this.polizaRepository.existsById(id)){
                var response = this.polizaService.consultarPolizaById(id);
                return ResponseEntity.ok(response);
            }

            LOG.error("La póliza: {} no existe.", id);
            return ResponseEntity.ok(FailResponse.build(ERROR_CONSULTAR_POLIZA));
        }catch(Exception ex){
            LOG.error(ERROR_CONSULTAR_POLIZA + " " + ex.toString());
            return ResponseEntity.ok(FailResponse.build(ERROR_CONSULTAR_POLIZA));
        }finally {
            LOG.info("Termina consulta de póliza por idPoliza");
        }
    }

    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<Map<String ,Object>> getPolizaByIdEmpleado(@PathVariable("idEmpleado") Long idEmpleado){
        try{
            LOG.info("Inicia consulta de póliza por idEmpleado: {}", idEmpleado);
            if(this.empleadoRepository.existsById(idEmpleado)){
                var response = this.polizaService.consultarPolizaPorIdEmpleado(idEmpleado);

                return ResponseEntity.ok(response);
            }
        }catch(Exception ex){
            LOG.error(ERROR_CONSULTAR_POLIZA + " " + ex.toString());
            return ResponseEntity.ok(FailResponse.build(ERROR_CONSULTAR_POLIZA));
        }finally {
            LOG.info("Termina consulta de póliza por idEmpleado");
        }

        return ResponseEntity.ok(FailResponse.build(ERROR_CONSULTAR_POLIZA));
    }

}
