package com.ulises.crudapi.model;

import com.ulises.crudapi.entity.Poliza;
import com.ulises.crudapi.entity.PolizaDetalle;

import java.util.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PolizaRequest {
    private Long idPoliza;
    private String empleadoGenero;
    private int cancelada;
    private Date fecha;
    private Date fechaCancelacion;
    private List<PolizaDetalleRequest> detalle = new ArrayList<>();
    public PolizaRequest(){}

    public static Poliza map(PolizaRequest polizaRequest){
        Poliza poliza = Poliza.build(
                polizaRequest.getIdPoliza(),
                polizaRequest.empleadoGenero,
                null, polizaRequest.getCancelada(), polizaRequest.getFechaCancelacion()
                ,mapPolizaDetalle(polizaRequest)
        );

        return poliza;
    }

    private static List<PolizaDetalle> mapPolizaDetalle(PolizaRequest polizaRequest){
        List<PolizaDetalle> detalles = new ArrayList<>();

        polizaRequest.getDetalle().forEach(
                (polizaDetalleRequest) -> {
                    var det = new PolizaDetalle(polizaDetalleRequest);
                    detalles.add(det);
                }
        );

        return detalles;
    }

    public String getEmpleadoGenero() {
        return empleadoGenero;
    }

    public void setEmpleadoGenero(String empleadoGenero) {
        this.empleadoGenero = empleadoGenero;
    }

    @Override
    public String toString() {
        return "PolizaRequest{" +
                "empleadoGenero='" + empleadoGenero + '\'' +
                '}';
    }

    public List<PolizaDetalleRequest> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<PolizaDetalleRequest> detalle) {
        this.detalle = detalle;
    }

    public Long getIdPoliza() {
        return idPoliza;
    }

    public void setIdPoliza(Long idPoliza) {
        this.idPoliza = idPoliza;
    }

    public int getCancelada() {
        return cancelada;
    }

    public void setCancelada(int cancelada) {
        this.cancelada = cancelada;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

}
