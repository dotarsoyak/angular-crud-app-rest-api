package com.ulises.crudapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(staticName = "build")
@Getter
@Setter
public class EmpleadoConsultaDto {
    private Long idEmpleado;
    private String nombre;
    private String apellido;

    public EmpleadoConsultaDto(){}

}
