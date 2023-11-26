package com.ulises.crudapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@ToString
@AllArgsConstructor(staticName = "build")
@Getter
public class Poliza {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long idPoliza;
    @Column(length = 100, nullable = false)
    private String empleadoGenero;
    @Column(name="fecha", columnDefinition = "date DEFAULT now()")
    private Date fecha;
    private int cancelada;
    @Column(name="fecha_cancelacion", columnDefinition = "date")
    private Date fechaCancelacion;

    @OneToMany(mappedBy = "poliza", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<PolizaDetalle> skus = new ArrayList<>();

    public Poliza(){}

    public Poliza(String empleadoGenero){
        setEmpleadoGenero(empleadoGenero);
    }

    public void setEmpleadoGenero(String empleadoGenero) {
        this.empleadoGenero = empleadoGenero;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setCancelada(int cancelada) {
        this.cancelada = cancelada;
    }

    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public void setSkus(List<PolizaDetalle> skus) {
        this.skus = skus;
    }
}
