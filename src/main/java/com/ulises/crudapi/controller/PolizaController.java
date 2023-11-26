package com.ulises.crudapi.controller;

import com.ulises.crudapi.entity.Poliza;
import com.ulises.crudapi.model.PolizaRequest;
import com.ulises.crudapi.repository.PolizaRepository;
import com.ulises.crudapi.service.PolizaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/poliza/api/v1")
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
    public ResponseEntity<String> actualizar(@RequestBody PolizaRequest polizaRequest){
        var poliza = this.polizaRepository.findById(polizaRequest.getIdPoliza());

        if(poliza.isPresent()){
            //polizaRequest.setFecha(poliza.get().getFecha());
            this.polizaService.actualizarPoliza(polizaRequest);
        }

        return null;

    }

    @GetMapping("/hi")
    public String hello(){
        return "Hello";
    }

}
