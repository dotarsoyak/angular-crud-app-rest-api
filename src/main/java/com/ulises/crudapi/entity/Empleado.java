package com.ulises.crudapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Empleado{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="IdEmpleado")
    private Long idEmpleado;
    @Column(name="Nombre")
    private String nombre;
    @Column(name="Apellido")
    private String apellido;
    @Column(name="Puesto")
    private String puesto;

    public Empleado(){}

    public Empleado(String nombre, String apellido, String puesto) {
        this.idEmpleado = 0L;
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setPuesto(puesto);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getPuesto() {
        return puesto;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "idEmpleado=" + idEmpleado +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", puesto='" + puesto + '\'' +
                '}';
    }
}
