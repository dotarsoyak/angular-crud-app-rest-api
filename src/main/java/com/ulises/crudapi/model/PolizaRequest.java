package com.ulises.crudapi.model;

import com.ulises.crudapi.entity.Poliza;
import com.ulises.crudapi.entity.PolizaDetalle;
import com.ulises.crudapi.enums.PolizaEnum;

import java.time.LocalDate;
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
                new Date( LocalDate.now().getYear()
                        , LocalDate.now().getMonthValue()
                        , LocalDate.now().getDayOfMonth()) //fecha
                ,0
                , polizaRequest.getFechaCancelacion()
                ,null //detalles de poliza
        );

        return poliza;
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

    public void setFechaCancelacion() {
        this.fechaCancelacion = Date.from(Instant.now());
    }

}
