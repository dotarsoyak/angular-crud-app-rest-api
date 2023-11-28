package com.ulises.crudapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.grammars.hql.HqlParser;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@ToString
@Getter
public class Poliza {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long idPoliza;
    @Column(length = 100, nullable = false)
    private String empleadoGenero;
    private Long idEmpleado;
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

    public void setFechaCancelacion(){
        this.fechaCancelacion = LocalDate.of(LocalDate.now().getYear()
                , LocalDate.now().getMonthValue()
                , LocalDate.now().getDayOfMonth());
    }

    public void setCancelada(int cancelada) {
        this.cancelada = cancelada;
    }

    public void setSkus(List<PolizaDetalle> skus) {
        this.skus = skus;
    }
}
