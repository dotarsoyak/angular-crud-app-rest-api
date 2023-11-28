package com.ulises.crudapi.controller;

import com.ulises.crudapi.entity.Inventario;
import com.ulises.crudapi.entity.Poliza;
import com.ulises.crudapi.model.DetalleArticuloModel;
import com.ulises.crudapi.model.EmpleadoActualizaRequest;
import com.ulises.crudapi.model.PolizaRequest;
import com.ulises.crudapi.repository.EmpleadoRepository;
import com.ulises.crudapi.repository.PolizaDetalleRepository;
import com.ulises.crudapi.repository.PolizaRepository;
import com.ulises.crudapi.response.ConsultarResponse;
import com.ulises.crudapi.response.FailResponse;
import com.ulises.crudapi.response.OkResponse;
import com.ulises.crudapi.response.data.DetalleArticuloResponseData;
import com.ulises.crudapi.response.data.EmpleadoResponseData;
import com.ulises.crudapi.response.data.PolizaResponseData;
import com.ulises.crudapi.service.PolizaService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            LOG.info("Objeto PolizaRequest: {}", polizaRequest);
            var grabadoResponse = this.polizaService.save(polizaRequest);

            URI location = ucb.path("/poliza/add/{id}")
                    .buildAndExpand(polizaRequest.getIdPoliza())
                    .toUri();

            return ResponseEntity.ok(grabadoResponse);
        }catch(Exception ex){
            return ResponseEntity.ok(FailResponse.build("Ha ocurrido un error en los grabado de póliza."));
        }

    }

    @GetMapping("/update/{idPoliza}/{idEmpleado}/{nombreEmpleado}")
    public ResponseEntity<Map<String, Object>> actualizarDatosEmpleado(
            @PathVariable("idPoliza") String idPoliza
            , @PathVariable("idEmpleado") String idEmpleado
            , @PathVariable("nombreEmpleado") String nombreEmpleado
    ){
        try{
            var polizaToUpdate =
                    this.polizaRepository.findById(Long.parseLong(idPoliza));

            if(polizaToUpdate.isPresent()){
                var empleadoActualizaRequest =
                        EmpleadoActualizaRequest.map(
                                idPoliza, idEmpleado, nombreEmpleado
                        );

                this.polizaService.actualizarPolizaDatosEmpleado(empleadoActualizaRequest, polizaToUpdate.get());
            }

            return ResponseEntity.ok(OkResponse.build("Se actualizó correctamente la póliza " + idPoliza));

        }catch(Exception e){
            return ResponseEntity.ok(FailResponse.build("Ha ocurrido un error al intentar actualizar la póliza."));
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id){
        try{
            if(this.polizaRepository.existsById(id)){
                this.polizaService.cancelarPoliza(id);
            }

            return ResponseEntity.ok(OkResponse.build("Se eliminó correctamente la poliza: " + id));
        }catch(Exception ex){
            return ResponseEntity.ok(FailResponse.build("Ha ocurrido un error al intentar obtener la póliza."));
        }
    }

    /*Consultar poliza*/
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable("id") Long id){
        try{
            if(this.polizaRepository.existsById(id)){
                //logica para obtener: Poliza, Empleado y DetalleArticulo
                var response = this.polizaService.consultarPolizaById(id);
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(FailResponse.build("Ha ocurrido un error al consultar la póliza."));

        }catch(Exception ex){
            return ResponseEntity.ok(FailResponse.build("Ha ocurrido un error al consultar la póliza."));
        }
    }

    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<Map<String ,Object>> getPolizaByIdEmpleado(@PathVariable("idEmpleado") Long idEmpleado){
        try{
            if(this.empleadoRepository.existsById(idEmpleado)){
                var response = this.polizaService.consultarPolizaPorIdEmpleado(idEmpleado);

                return ResponseEntity.ok(response);
            }
        }catch(Exception ex){
            return ResponseEntity.ok(FailResponse.build("Ha ocurrido un error al consultar la póliza"));
        }

        return ResponseEntity.ok(FailResponse.build("Ha ocurrido un error al consultar la póliza"));
    }

    @GetMapping("/hi")
    public void hello(){
        var detallesSkus =
                jdbcTemplate.queryForList("select * from obtenerDetalleArticulosPorIdPoliza(1)");

        System.out.println();

    }

}
