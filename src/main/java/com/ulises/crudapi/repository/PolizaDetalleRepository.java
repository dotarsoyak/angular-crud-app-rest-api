package com.ulises.crudapi.repository;

import com.ulises.crudapi.entity.Poliza;
import com.ulises.crudapi.entity.PolizaDetalle;
import com.ulises.crudapi.entity.PolizaDetallePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolizaDetalleRepository extends JpaRepository<PolizaDetalle, PolizaDetallePk> {
}
