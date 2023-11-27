package com.ulises.crudapi.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PolizaDetallePk implements Serializable {
    private Long idPoliza;
    private String sku;

    public PolizaDetallePk(){}

    public PolizaDetallePk(Long idPoliza, String sku) {
        this.idPoliza = idPoliza;
        this.sku = sku;
    }

    public void setIdPoliza(Long idPoliza) {
        this.idPoliza = idPoliza;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getIdPoliza() {
        return idPoliza;
    }

    public String getSku() {
        return sku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolizaDetallePk that = (PolizaDetallePk) o;
        return Objects.equals(idPoliza, that.idPoliza) && Objects.equals(sku, that.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPoliza, sku);
    }

}
