package com.ulises.crudapi.service;

import com.ulises.crudapi.entity.Poliza;
import com.ulises.crudapi.model.EmpleadoActualizaRequest;
import com.ulises.crudapi.model.PolizaRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PolizaService {
    Poliza save(PolizaRequest polizaRequest);
    void actualizarPolizaDatosEmpleado(EmpleadoActualizaRequest empleadoActualizaRequest, Poliza polizaToUpdate);
    void cancelarPoliza(Long idPoliza);

    Map<String, Object> consultarPolizaById(Long id);
}
