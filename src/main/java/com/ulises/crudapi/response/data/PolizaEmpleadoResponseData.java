package com.ulises.crudapi.response.data;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@ToString
public class PolizaEmpleadoResponseData {
    private Long idPoliza;
    private Long cantidad;
    private LocalDate fecha;
}
