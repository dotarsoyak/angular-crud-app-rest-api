package com.ulises.crudapi.response;

import com.ulises.crudapi.response.data.DetalleArticuloResponseData;
import com.ulises.crudapi.response.data.EmpleadoResponseData;
import com.ulises.crudapi.response.data.PolizaResponseData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultarResponse {
    public static Map<String, Object> build(
            PolizaResponseData poliza
            , EmpleadoResponseData empleadoData, List<DetalleArticuloResponseData> articuloList
    ){
        Map<String, String> statusMeta = new HashMap<>();
        statusMeta.put("Status", "OK");
        //polizaMeta
        Map<String, Object> data = new HashMap<>();
        data.put("Poliza", poliza);

        //empleadoMeta - objeto Empleado
        Map<String, Object> empleadoMeta = new HashMap<>();
        data.put("Empleado", empleadoData);

        //detalleArticuloMeta List<Inventario>
        Map<String, Object> detalleArticuloMap = new HashMap<>();
        data.put("DetalleArticulo", articuloList);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("Meta", statusMeta);
        response.put("Data", data);

        return response;
    }
}
