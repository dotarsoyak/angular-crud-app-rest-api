package com.ulises.crudapi.controller;

import com.ulises.crudapi.entity.Poliza;
import com.ulises.crudapi.model.PolizaRequest;
import com.ulises.crudapi.repository.PolizaDetalleRepository;
import com.ulises.crudapi.repository.PolizaRepository;
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

    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> actualizar(@RequestBody PolizaRequest polizaRequest){
        var poliza = this.polizaRepository.findById(polizaRequest.getIdPoliza());

        if(poliza.isPresent()){
            this.polizaService.actualizarPoliza(polizaRequest);

            return ResponseEntity.ok(
                    OkResponse.build("Se actualizó correctamente la póliza "
                    + polizaRequest.getIdPoliza()));
        }

        return ResponseEntity.ok(OkResponse.build("Ha ocurrido un error al intentar actualiza la póliza."));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        if(this.polizaRepository.existsById(id)){
            //logica para actualizar la poliza con estatus cancelada = 1
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/hi")
    public ResponseEntity<Map<String, Object>> hello(){
        //this.polizaDetalleRepository.deletePolizaDetalleByIdPoliza(3);
        var response = OkResponse.build("Actualizado ok");

        return ResponseEntity.ok(response);
    }

}
