package com.ulises.crudapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ulises.crudapi.model.PolizaDetalleRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@AllArgsConstructor
@Getter
@Entity
public class PolizaDetalle {
    @EmbeddedId
    PolizaDetallePk entityPK;
    private int cantidad;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "poliza_id_poliza")
    @JsonIgnore
    private Poliza poliza;
    public PolizaDetalle(){}

    public PolizaDetalle(PolizaDetalleRequest detalle){
        var pk = new PolizaDetallePk();
        pk.setIdPoliza(detalle.getIdPoliza());
        pk.setSku(detalle.getSku());
        this.setEntityPK(pk);
        this.setCantidad(detalle.getCantidad());
    }

    public PolizaDetallePk getEntityPK() {
        return entityPK;
    }

    public void setEntityPK(PolizaDetallePk entityPK) {
        this.entityPK = entityPK;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
