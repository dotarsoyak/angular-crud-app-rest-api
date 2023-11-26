package com.ulises.crudapi.service;

import com.ulises.crudapi.entity.Inventario;
import com.ulises.crudapi.entity.PolizaDetalle;

import java.util.Optional;

public interface InventarioService {
    Optional<Inventario> updateInventory(PolizaDetalle polizaDetalle);
}
