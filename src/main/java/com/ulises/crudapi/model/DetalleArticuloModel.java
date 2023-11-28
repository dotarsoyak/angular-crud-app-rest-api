package com.ulises.crudapi.model;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Getter
@Setter
public class DetalleArticuloModel {
    private String sku;
    private String nombre;
}
