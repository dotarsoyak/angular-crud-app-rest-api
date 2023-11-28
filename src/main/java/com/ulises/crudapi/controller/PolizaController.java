package com.ulises.crudapi.controller;

import com.ulises.crudapi.entity.Poliza;
import com.ulises.crudapi.model.EmpleadoActualizaRequest;
import com.ulises.crudapi.model.PolizaRequest;
import com.ulises.crudapi.repository.PolizaDetalleRepository;
import com.ulises.crudapi.repository.PolizaRepository;
import com.ulises.crudapi.response.FailResponse;
import com.ulises.crudapi.response.OkResponse;
import com.ulises.crudapi.service.PolizaService;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/poliza")
public class PolizaController {
    private static final Logger LOG = LoggerFactory.getLogger(PolizaController.class);

    @Autowired
    private PolizaService polizaService;
    @Autowired
    private PolizaRepository polizaRepository;

    @PostMapping("/add")
    public ResponseEntity<Poliza> save(@RequestBody PolizaRequest polizaRequest
            , UriComponentsBuilder ucb) {

        LOG.info("Objeto PolizaRequest: {}", polizaRequest);
        var createdPoliza = this.polizaService.save(polizaRequest);

        URI location = ucb.path("/poliza/add/{id}")
                .buildAndExpand(createdPoliza.getIdPoliza())
                .toUri();

        return ResponseEntity.created(location).build();
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
            return ResponseEntity.ok(FailResponse.build("Ha ocurrido un error al intentar eliminar la póliza."));
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
            return ResponseEntity.ok(FailResponse.build("Ha ocurrido un error al intentar eliminar la póliza."));
        }
    }

    @GetMapping("/hi")
    public ResponseEntity<Map<String, Object>> hello(){
        //this.polizaDetalleRepository.deletePolizaDetalleByIdPoliza(3);
        var response = OkResponse.build("Actualizado ok");

        return ResponseEntity.ok(response);
    }

}
