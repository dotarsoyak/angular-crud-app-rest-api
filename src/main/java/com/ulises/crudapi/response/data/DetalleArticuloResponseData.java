package com.ulises.crudapi.response.data;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor(staticName = "build")
public class DetalleArticuloResponseData {
    private String sku;
    private String nombre;

    public DetalleArticuloResponseData(){}


}
