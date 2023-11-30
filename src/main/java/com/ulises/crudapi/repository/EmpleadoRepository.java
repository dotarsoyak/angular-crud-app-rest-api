package com.ulises.crudapi.repository;

import com.ulises.crudapi.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    List<Empleado> findAll();
    List<Empleado> findAllByNombreContainsIgnoreCase(String nombre);
}
