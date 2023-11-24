package com.ulises.crudapi.model;

import com.ulises.crudapi.entity.Empleado;

public class EmpleadoRequest {
    private Long idEmpleado;
    private String nombre;
    private String apellido;
    private String puesto;

    public EmpleadoRequest(){}

    public static Empleado map(EmpleadoRequest empleadoRequest){
        Empleado empleado = new Empleado(
                empleadoRequest.nombre,
                empleadoRequest.apellido,
                empleadoRequest.puesto);

        return empleado;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    @Override
    public String toString() {
        return "EmpleadoRequest{" +
                "idEmpleado=" + idEmpleado +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", puesto='" + puesto + '\'' +
                '}';
    }
}
