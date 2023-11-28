package com.ulises.crudapi.model;

public class EmpleadoActualizaRequest {
    private String idPoliza;
    private String idEmpleado;
    private String nombreEmpleado;

    public EmpleadoActualizaRequest(){}
    public EmpleadoActualizaRequest(String idPoliza, String idEmpleado, String nombreEmpleado){
        this.setIdPoliza(idPoliza);
        this.setIdEmpleado(idEmpleado);
        this.setNombreEmpleado(nombreEmpleado);
    }

    public static EmpleadoActualizaRequest map(String idPoliza, String idEmpleado, String nombreEmpleado){
        return new EmpleadoActualizaRequest(idPoliza, idEmpleado, nombreEmpleado);
    }

    public String getIdPoliza() {
        return idPoliza;
    }

    public void setIdPoliza(String idPoliza) {
        this.idPoliza = idPoliza;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    @Override
    public String toString() {
        return "EmpleadoActualizaRequest{" +
                "idPoliza='" + idPoliza + '\'' +
                ", idEmpleado='" + idEmpleado + '\'' +
                ", nombreEmpleado='" + nombreEmpleado + '\'' +
                '}';
    }
}
