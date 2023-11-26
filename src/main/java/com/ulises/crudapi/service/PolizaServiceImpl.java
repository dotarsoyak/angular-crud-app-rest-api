package com.ulises.crudapi.service;

import com.ulises.crudapi.entity.Poliza;
import com.ulises.crudapi.entity.PolizaDetalle;
import com.ulises.crudapi.entity.PolizaDetallePk;
import com.ulises.crudapi.model.PolizaRequest;
import com.ulises.crudapi.repository.PolizaRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PolizaServiceImpl implements PolizaService {
    private static final int CANCELADA = 1;
    @Autowired
    private EntityManager em;

    @Autowired
    private InventarioServiceImpl inventarioService;

    @Autowired
    private PolizaRepository polizaRepository;

    @Override
    @Transactional
    public Poliza save(PolizaRequest polizaRequest) {
        Poliza poliza = PolizaRequest.map(polizaRequest);
        poliza.setFecha(Date.from(Instant.now()));

        var polizaDetalle = new ArrayList<PolizaDetalle>(poliza.getSkus());

        poliza.setSkus(null);

        em.persist(poliza);
        em.merge(poliza);

        List<PolizaDetalle> detalles = new ArrayList<>();

        polizaRequest.getDetalle().forEach(
                (polizaDetalleRequest) -> {
                    polizaDetalleRequest.setIdPoliza(poliza.getIdPoliza());
                    var det = new PolizaDetalle(polizaDetalleRequest);
                    detalles.add(det);
                    em.persist(det);

                    //TODO: agregar flag para aumentar o disminuir el inventario
                    //aumentamos o disminuimos el inventario
                    inventarioService.updateInventory(det);
                }
        );

        em.flush();

        //poliza.setSkus(detalles);

        return this.polizaRepository.save(poliza);

    }

    @Override
    @Transactional
    public void actualizarPoliza(PolizaRequest polizaRequest) {
        var p = PolizaRequest.map(polizaRequest);
        var polizaFromRepository = this.polizaRepository.findById(polizaRequest.getIdPoliza());

        /*
        * Actualizar empleadoGenero, fecha, cancelada, fechaCancelacion, skus
        * */

        var polizaToUpdate = polizaFromRepository.get();

        actualizarPolizaMaestro(p, polizaToUpdate);

        em.persist(polizaToUpdate);
        em.merge(polizaToUpdate);

        //eliminar los actuales skus
        polizaFromRepository.get().getSkus().forEach(
                (polizaDetalle) -> {
                    em.remove(polizaDetalle);
                    em.merge(polizaDetalle);
                }
        );

        //insertar los nuevos skus

        polizaToUpdate.getSkus().forEach(
            (polizaDetalle) -> {
                em.persist(polizaDetalle);
            }
        );

        em.flush();

        polizaToUpdate.setSkus(p.getSkus());

    }

    private static void actualizarPolizaMaestro(Poliza p, Poliza polizaToUpdate) {
        polizaToUpdate.setEmpleadoGenero(p.getEmpleadoGenero());

        if(p.getFecha() != null){
            polizaToUpdate.setFecha(p.getFecha());
        }

        polizaToUpdate.setCancelada(p.getCancelada());

        if(p.getCancelada() == CANCELADA) {
            polizaToUpdate.setFechaCancelacion(p.getFechaCancelacion());
        }
    }

}
