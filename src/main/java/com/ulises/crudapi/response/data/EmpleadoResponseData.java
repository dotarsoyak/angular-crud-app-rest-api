package com.ulises.crudapi.response.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmpleadoResponseData {
    private String nombre;
    private String apellido;

    public EmpleadoResponseData(){}

    public EmpleadoResponseData(String nombre, String apellido){
        this.setNombre(nombre);
        this.setApellido(apellido);
    }


}
