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
    private Long idEmpleado;
    private String empleadoGenero;
    private int cancelada;
    private Date fecha;
    private Date fechaCancelacion;
    private List<PolizaDetalleRequest> detalle = new ArrayList<>();
    public PolizaRequest(){}

    public static Poliza map(PolizaRequest polizaRequest){
        Poliza poliza = new Poliza(
                polizaRequest.getIdEmpleado(),//idpoliza
                polizaRequest.empleadoGenero //empleado genero
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
                "idPoliza=" + idPoliza +
                ", idEmpleado=" + idEmpleado +
                ", empleadoGenero='" + empleadoGenero + '\'' +
                ", cancelada=" + cancelada +
                ", fecha=" + fecha +
                ", fechaCancelacion=" + fechaCancelacion +
                ", detalle=" + detalle +
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

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }
}
