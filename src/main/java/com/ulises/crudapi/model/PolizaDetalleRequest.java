package com.ulises.crudapi.model;

import com.ulises.crudapi.entity.Poliza;
import com.ulises.crudapi.entity.PolizaDetalle;

import java.util.ArrayList;
import java.util.List;

public class PolizaDetalleRequest {
    private long idPoliza = 0L;
    private String sku;
    private int cantidad;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setIdPoliza(long idPoliza) {
        this.idPoliza = idPoliza;
    }

    public long getIdPoliza() {
        return idPoliza;
    }

    @Override
    public String toString() {
        return "PolizaDetalleRequest{" +
                "sku='" + sku + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}
