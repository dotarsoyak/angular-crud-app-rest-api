package com.ulises.crudapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@ToString
public class Poliza {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long idPoliza;
    private Long idEmpleado;
    @Column(length = 100, nullable = false)
    private String empleadoGenero;
    @Column(name="fecha", columnDefinition = "DATE")
    private LocalDate fecha;
    private int cancelada;
    @Column(name="fecha_cancelacion", columnDefinition = "DATE")
    private LocalDate fechaCancelacion;

    @OneToMany(mappedBy = "poliza", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<PolizaDetalle> skus = new ArrayList<>();

    public Poliza(){}

    public Poliza(String empleadoGenero){
        setEmpleadoGenero(empleadoGenero);
    }

    public Poliza(Long idEmpleado, String empleadoGenero){
        this.idEmpleado = idEmpleado;
        this.empleadoGenero = empleadoGenero;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setEmpleadoGenero(String empleadoGenero) {
        this.empleadoGenero = empleadoGenero;
    }

    public void setFechaActual() {
        this.fecha =
                LocalDate.of( LocalDate.now().getYear()
                        , LocalDate.now().getMonthValue()
                        , LocalDate.now().getDayOfMonth());
    }

    public void setCancelada(int cancelada) {
        this.cancelada = cancelada;
    }

    public void setSkus(List<PolizaDetalle> skus) {
        this.skus = skus;
    }

    public void setFechaCancelacion(LocalDate fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

}
