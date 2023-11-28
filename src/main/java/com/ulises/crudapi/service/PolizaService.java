package com.ulises.crudapi.service;

import com.ulises.crudapi.entity.Poliza;
import com.ulises.crudapi.model.EmpleadoActualizaRequest;
import com.ulises.crudapi.model.PolizaRequest;

import java.util.Map;

public interface PolizaService {
    Map<String, Object> save(PolizaRequest polizaRequest);
    void actualizarPolizaDatosEmpleado(EmpleadoActualizaRequest empleadoActualizaRequest, Poliza polizaToUpdate);
    void cancelarPoliza(Long idPoliza);
    Map<String, Object> consultarPolizaById(Long id);

    Map<String, Object> consultarPolizaPorIdEmpleado(Long idEmpleado);
}
