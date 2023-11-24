package com.ulises.crudapi.service;

import com.ulises.crudapi.entity.Empleado;
import com.ulises.crudapi.model.EmpleadoRequest;
import com.ulises.crudapi.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public Empleado save(EmpleadoRequest empleadoRequest) {
        Empleado empleado = EmpleadoRequest.map(empleadoRequest);
        return this.empleadoRepository.save(empleado);
    }

}
