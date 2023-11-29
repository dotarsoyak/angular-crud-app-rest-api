package com.ulises.crudapi.service;

import com.ulises.crudapi.entity.Poliza;
import com.ulises.crudapi.entity.PolizaDetalle;
import com.ulises.crudapi.enums.PolizaEnum;
import com.ulises.crudapi.model.DetalleArticuloModel;
import com.ulises.crudapi.model.EmpleadoActualizaRequest;
import com.ulises.crudapi.model.PolizaRequest;
import com.ulises.crudapi.repository.EmpleadoRepository;
import com.ulises.crudapi.repository.PolizaRepository;
import com.ulises.crudapi.response.ConsultarResponse;
import com.ulises.crudapi.response.GrabadoResponse;
import com.ulises.crudapi.response.data.DetalleArticuloResponseData;
import com.ulises.crudapi.response.data.EmpleadoResponseData;
import com.ulises.crudapi.response.data.PolizaEmpleadoResponseData;
import com.ulises.crudapi.response.data.PolizaResponseData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

@Service
public class PolizaServiceImpl implements PolizaService {
    @Autowired
    private EntityManager em;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private InventarioServiceImpl inventarioService;

    @Autowired
    private PolizaRepository polizaRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public Map<String, Object> consultarPolizaPorIdEmpleado(Long idEmpleado) {
        var polizaEmpleadoList = this.polizaRepository.findByIdEmpleado(idEmpleado);
        var polizaResponseDataList = new ArrayList<PolizaEmpleadoResponseData>();

        //Armar polizaResponseDataList
        polizaEmpleadoList
                .stream().forEach(
                        (Poliza p) -> {
                            var polizaResponseData =
                                    PolizaEmpleadoResponseData.build(p.getIdPoliza()
                                            , p.getSkus().stream().count(), p.getFecha());
                            polizaResponseDataList.add(polizaResponseData);
                        }
                );

        //crear objeto ConsultaResponse
        var consultaResponse =
                ConsultarResponse.buildPolizaList(
                        polizaResponseDataList
                );

        return consultaResponse;

    }

    @Override
    public Map<String, Object> consultarPolizaById(Long id) {
        var poliza = this.polizaRepository.findById(id).get();
        var empleado = this.empleadoRepository.findById(poliza.getIdEmpleado()).get();
        var polizaResponseData = new PolizaResponseData();

        polizaResponseData.setIdPoliza(id);
        polizaResponseData.setCantidad(poliza.getSkus().stream().count());

        var empleadoResponseData = new EmpleadoResponseData();
        empleadoResponseData.setNombre(empleado.getNombre().trim());
        empleadoResponseData.setApellido(empleado.getApellido().trim());

        var detalleArticuloResponseData = new DetalleArticuloResponseData();

        //obtener los skus de la base de datos
        //TODO: Refactorizar esta parte para consumirla obtener una lista como resultado
        //TODO: parametrizar esta consulta
        var detallesSkus = jdbcTemplate
                        .queryForList("select sku, nombre from obtenerDetalleArticulosPorIdPoliza("+id+")");

        //TODO: Asignar al DetalleArticuloResponseList, la responsabilidad de mapear este codigo.
        //o en su lugar, crear un helper de DetalleArticuloResponseList para que haga el mapeo
        var detalleArticuloList = new ArrayList<DetalleArticuloModel>();
                detallesSkus.forEach((s) -> {
                    detalleArticuloList.add(DetalleArticuloModel.build((String)s.get("sku").toString().trim()
                            ,(String)s.get("nombre").toString().trim()));
                }
        );

        var detalleArticuloResponseDataList =
                detalleArticuloList
                        .stream()
                        .map((DetalleArticuloModel det) -> {
                                  return DetalleArticuloResponseData.build(det.getSku(), det.getNombre());
                                }).toList();


        //crear objeto ConsultaResponse
        var consultaResponse =
                ConsultarResponse.build(
                        polizaResponseData
                        ,empleadoResponseData
                        ,detalleArticuloResponseDataList
                );

        return consultaResponse;
    }

    @Override
    public void cancelarPoliza(Long id){
        var poliza = this.polizaRepository.findById(id);

        //establecer cancelada = 1 y fechaCancelacion = fecha actual
        var polizaToDelete = poliza.get();

        polizaToDelete.setCancelada(PolizaEnum.CANCELADA.ordinal());
        polizaToDelete.setFechaCancelacion(
                LocalDate.of(LocalDate.now().getYear()
                , LocalDate.now().getMonthValue()
                , LocalDate.now().getDayOfMonth()));

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
    public Map<String, Object> save(PolizaRequest polizaRequest) {
        Poliza poliza = PolizaRequest.map(polizaRequest);
        poliza.setFechaActual();

        em.persist(poliza);
        em.merge(poliza);
        poliza.setSkus(new ArrayList<PolizaDetalle>());//reiniciamos su lista de skus

        polizaRequest.setIdPoliza(poliza.getIdPoliza()); //seteamos la poliza id para recuperarla en el controller

        polizaRequest.getDetalle().forEach(
                (polizaDetalleRequest) -> {
                    polizaDetalleRequest.setIdPoliza(poliza.getIdPoliza());
                    var det = new PolizaDetalle(polizaDetalleRequest);
                    det.setPoliza(poliza);
                    em.persist(det);
                    inventarioService.disminuirInventario(det);
                }
        );

        em.persist(poliza);
        em.flush();
        em.close();


        //Armar objeto de respuesta
        var polizaResponseData = new PolizaResponseData();

        polizaResponseData.setIdPoliza(poliza.getIdPoliza());
        polizaResponseData.setCantidad(polizaRequest.getDetalle().stream().count());

        var empleado = this.empleadoRepository.findById(poliza.getIdEmpleado()).get();
        var empleadoResponseData = new EmpleadoResponseData();
        empleadoResponseData.setNombre(empleado.getNombre().trim());
        empleadoResponseData.setApellido(empleado.getApellido().trim());

        var detallesSkus = jdbcTemplate
                .queryForList("select sku, nombre from obtenerDetalleArticulosPorIdPoliza("+poliza.getIdPoliza()+")");

        //TODO: Refactorizar esta parte: relacionar PolizaDetalle con Inventario
        //para poder obtener de polizaDetalle la relacion de inventario.
        var detalleArticuloList = new ArrayList<DetalleArticuloModel>();
        detallesSkus.forEach((s) -> {
                    detalleArticuloList.add(DetalleArticuloModel.build((String)s.get("sku").toString().trim()
                            ,(String)s.get("nombre").toString().trim()));
                }
        );
        var detalleArticuloResponseDataList =
                detalleArticuloList
                        .stream()
                        .map((DetalleArticuloModel det) -> {
                            return DetalleArticuloResponseData.build(det.getSku(), det.getNombre());
                        }).toList();

        return GrabadoResponse.build(
                polizaResponseData
                ,empleadoResponseData
                ,detalleArticuloResponseDataList
        );
    }



}
