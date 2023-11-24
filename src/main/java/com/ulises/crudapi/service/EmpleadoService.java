package com.ulises.crudapi.service;

import com.ulises.crudapi.entity.Empleado;
import com.ulises.crudapi.model.EmpleadoRequest;

public interface EmpleadoService {
    Empleado save(EmpleadoRequest empleadoRequest);
}
