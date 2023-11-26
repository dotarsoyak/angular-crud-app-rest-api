package com.ulises.crudapi.service;

import com.ulises.crudapi.entity.Poliza;
import com.ulises.crudapi.model.PolizaRequest;

public interface PolizaService {
    Poliza save(PolizaRequest polizaRequest);

    void actualizarPoliza(PolizaRequest polizaRequest);
}
