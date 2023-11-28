package com.ulises.crudapi.service;

import com.ulises.crudapi.entity.Poliza;
import com.ulises.crudapi.entity.PolizaDetalle;
import com.ulises.crudapi.enums.PolizaEnum;
import com.ulises.crudapi.model.EmpleadoActualizaRequest;
import com.ulises.crudapi.model.PolizaRequest;
import com.ulises.crudapi.repository.PolizaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PolizaServiceImpl implements PolizaService {
    @Autowired
    private EntityManager em;

    @Autowired
    private InventarioServiceImpl inventarioService;

    @Autowired
    private PolizaRepository polizaRepository;

    @Override
    public void cancelarPoliza(Long id){
        var poliza = this.polizaRepository.findById(id);

        //establecer cancelada = 1 y fechaCancelacion = fecha actual
        var polizaToDelete = poliza.get();

        polizaToDelete.setCancelada(PolizaEnum.CANCELADA.ordinal());
        polizaToDelete.setFechaCancelacion();

        //grabar
        this.polizaRepository.save(polizaToDelete);
    }

    @Override
    public void actualizarPolizaDatosEmpleado(EmpleadoActualizaRequest empleadoActualizaRequest, Poliza polizaToUpdate) {
        polizaToUpdate.setIdEmpleado(Long.parseLong(empleadoActualizaRequest.getIdEmpleado()));
        polizaToUpdate.setEmpleadoGenero(empleadoActualizaRequest.getNombreEmpleado());

        this.polizaRepository.save(polizaToUpdate);
    }

    @Override
    @Transactional
    public Poliza save(PolizaRequest polizaRequest) {
        Poliza poliza = PolizaRequest.map(polizaRequest);
        poliza.setFechaActual();

        em.persist(poliza);
        em.merge(poliza);
        poliza.setSkus(new ArrayList<PolizaDetalle>());

        polizaRequest.getDetalle().forEach(
                (polizaDetalleRequest) -> {
                    polizaDetalleRequest.setIdPoliza(poliza.getIdPoliza());
                    var det = new PolizaDetalle(polizaDetalleRequest);
                    //det.setPoliza(poliza);
                    em.persist(det);
                    inventarioService.disminuirInventario(det);
                }
        );

        em.persist(poliza);
        em.flush();
        em.close();

        return poliza;
    }


}
