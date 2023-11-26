package com.ulises.crudapi.service;

import com.ulises.crudapi.entity.Inventario;
import com.ulises.crudapi.entity.PolizaDetalle;
import com.ulises.crudapi.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventarioServiceImpl implements InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    public Optional<Inventario> updateInventory(PolizaDetalle polizaDetalle) {
        var singleSku = getInventario(polizaDetalle);
        var sku = this.inventarioRepository.findById(singleSku.getSku());

        if(sku.isPresent()){
            //actualizamos
            var selectedSku = sku.get();
            selectedSku.disminuirCantidad(singleSku.getCantidad());
            this.inventarioRepository.save(selectedSku);
        }

        return Optional.ofNullable(new Inventario());
    }

    private static Inventario getInventario(PolizaDetalle polizaDetalle){
        return new Inventario(polizaDetalle.getEntityPK().getSku(), polizaDetalle.getCantidad());
    }

}
