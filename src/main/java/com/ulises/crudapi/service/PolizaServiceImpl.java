package com.ulises.crudapi.service;

import com.ulises.crudapi.entity.Poliza;
import com.ulises.crudapi.entity.PolizaDetalle;
import com.ulises.crudapi.entity.PolizaDetallePk;
import com.ulises.crudapi.enums.PolizaEnum;
import com.ulises.crudapi.model.PolizaDetalleRequest;
import com.ulises.crudapi.model.PolizaRequest;
import com.ulises.crudapi.repository.PolizaDetalleRepository;
import com.ulises.crudapi.repository.PolizaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PolizaServiceImpl implements PolizaService {
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

    @Override
    @Transactional
    public void actualizarPoliza(PolizaRequest polizaRequest) {
        var mappedPoliza = PolizaRequest.map(polizaRequest);
        var polizaFromRepository = this.polizaRepository.findById(polizaRequest.getIdPoliza());
        var polizaToUpdate = polizaFromRepository.get();

        //setear encabezados
        actualizarPolizaMaestro(mappedPoliza, polizaToUpdate);

        //generar lista actualizada para grabar en el detalle de la poliza
        var detalleActualizado = getPolizaDetallesActualizada(polizaRequest);

        //eliminar detalles
        eliminarDetalles(polizaRequest);

        polizaToUpdate.setSkus(new ArrayList<PolizaDetalle>());

        //insertar los skus actualizados
        detalleActualizado.forEach(
            (PolizaDetalle det) -> {
                //det.setPoliza(polizaToUpdate);
                em.persist(det);

                if(polizaToUpdate.getCancelada() == PolizaEnum.CANCELADA.ordinal()){
                    inventarioService.incrementarInventario(det);
                }
            }
        );

        em.persist(polizaToUpdate);
        em.flush();
        em.close();
    }

    private static List<PolizaDetalle> getPolizaDetallesActualizada(PolizaRequest polizaRequest) {
        List<PolizaDetalle> detalleActualizado = new ArrayList<>(
                polizaRequest.getDetalle()
                        .stream()
                        .map(
                        (PolizaDetalleRequest det) -> {
                            return new PolizaDetalle(det);
                        }).toList()
        );
        return detalleActualizado;
    }

    private void eliminarDetalles(PolizaRequest polizaRequest) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("deletePolizaDetalleByIdPoliza")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .setParameter(1, polizaRequest.getIdPoliza());

        query.execute();

    }

    private static void actualizarPolizaMaestro(Poliza mappedPoliza, Poliza polizaToUpdate) {
        polizaToUpdate.setEmpleadoGenero(mappedPoliza.getEmpleadoGenero());
        polizaToUpdate.setCancelada(mappedPoliza.getCancelada());

        if(mappedPoliza.getCancelada() == PolizaEnum.CANCELADA.ordinal()) {
            polizaToUpdate.setFechaCancelacion(mappedPoliza.getFechaCancelacion());
        }
    }

}
