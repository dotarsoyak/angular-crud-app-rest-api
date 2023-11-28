package com.ulises.crudapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@ToString
@AllArgsConstructor
@Getter
@Setter
public class Inventario {
    @Id
    @Column(length = 10, nullable = false, columnDefinition = "CHAR(10)")
    private String sku;
    @Column(length = 100, nullable = false, columnDefinition = "CHAR(100)")
    private String nombre;
    @Column(nullable = false)
    private int cantidad;

    public Inventario(){}

    public Inventario(String sku, int cantidad){
        this.setSku(sku);
        this.setCantidad(cantidad);
    }

    public void disminuirCantidad(int cantidad){
        this.setCantidad(this.getCantidad() - cantidad);
    }

    public void aumentarCantidad(int cantidad){
        this.setCantidad(this.getCantidad() + cantidad);
    }

}
